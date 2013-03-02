/*
 * This source file is part of OSTIS (Open Semantic Technology for Intelligent
 * Systems) For the latest info, see http://www.ostis.net
 *
 * Copyright (c) 2010 OSTIS
 *
 * OSTIS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * OSTIS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with OSTIS. If not, see <http://www.gnu.org/licenses/>.
 */
package net.ostis.scpdev.wizards;

import java.lang.reflect.InvocationTargetException;

import net.ostis.scpdev.ScpdevPlugin;
import net.ostis.scpdev.core.ScNature;
import net.ostis.scpdev.util.Resources;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyOperation;

/**
 * @author Dmitry Lazurkin
 */
public class RepositoryProjectNewWizard extends Wizard implements INewWizard {
    /**
     * The main page of the wizard: collects the project name and location.
     */
    private RepositoryProjectNewWizardPage mainPage;

    /**
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
     *      org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        setNeedsProgressMonitor(true);
    }

    /**
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    public void addPages() {
        try {
            super.addPages();
            mainPage = new RepositoryProjectNewWizardPage("NewSCRepositoryProjectPage"); //$NON-NLS-1$
            mainPage.setTitle(Resources.getString("eclipse.newprojectname"));
            mainPage.setDescription(Resources.getString("eclipse.newprojectdescription"));
            addPage(mainPage);
        } catch (Exception x) {
            reportError(x);
        }
    }

    /**
     * Displays an error that occured during the project creation.
     */
    private void reportError(Exception x) {
        ErrorDialog.openError(
                getShell(),
                Resources.getString("eclipse.dialogtitle"),
                Resources.getString("eclipse.projecterror"),
                new Status(IStatus.ERROR, ScpdevPlugin.PLUGIN_ID, IStatus.ERROR, x.getMessage() != null ? x
                        .getMessage() : x.toString(), x));
    }

    /**
     * User has clicked "Finish", we create the project. In practice, it calls
     * the createProject() method in the appropriate thread.
     *
     * @see #createProject(IProjectMonitor)
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    public boolean performFinish() {
        try {
            getContainer().run(false, true, new WorkspaceModifyOperation() {
                protected void execute(IProgressMonitor monitor) {
                    createProject(monitor != null ? monitor : new NullProgressMonitor());
                }
            });
        } catch (InvocationTargetException x) {
            reportError(x);
            return false;
        } catch (InterruptedException x) {
            reportError(x);
            return false;
        }
        return true;
    }

    /**
     * This is the actual implementation for project creation.
     */
    protected void createProject(IProgressMonitor monitor) {
        monitor.beginTask(Resources.getString("eclipse.creatingproject"), 50);
        try {
            // извлекаем корень рабочего пространства
        	// через plugin ResourcesPlugin получаем доступ к рабочему пространству (ресурсу)
        	IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
            monitor.subTask(Resources.getString("eclipse.creatingdirectories"));
            
            // создаем проект методом getProject из IWorkspaceRoot, имя проекта задается из
            // соответствующего поля, введенного пользователем
            IProject project = root.getProject(mainPage.getProjectName());
            
            // создаем описание проекта методом newProjectDescription
            IProjectDescription description = ResourcesPlugin.getWorkspace().newProjectDescription(project.getName());
            
            // если расположение рабочего каталога не совпадает с путем, введенным пользователем, 
            //  то для описываемого проекта задаем расположение пректа в файловой системе
            if (!Platform.getLocation().equals(mainPage.getLocationPath()))
                description.setLocation(mainPage.getLocationPath());
            
            // создание проекта
            project.create(description, monitor);
            monitor.worked(10);
            
            //открываем проект
            project.open(monitor);
            //задаем описание 
            description = project.getDescription();
            //   добавление в информацию о проекте идентификатора сущности NATURE_ID = "net.ostis.scpdev.sc_nature"
            //из ScNature.java
            //   идентификатор сущности определеяет принадлежность проекта определенному типу
            //   проверка сущности проекта часто используется в пользовательском интерфейсе, 
            //когда необходимо применить какое-то действие только к проектам данного типа
            description.setNatureIds(new String[] {
                ScNature.NATURE_ID
            });
            //изменение проекта в соответствии с описанием description
            project.setDescription(description, new SubProgressMonitor(monitor, 10));
            // получаем абсолютный путь относительно рабочего пространства
            IPath projectPath = project.getFullPath();
            // задаем путь к src
            IPath srcPath = projectPath.append("fs_repo_src");
            // получаем дискриптор каталога srcPath
            IFolder srcFolder = root.getFolder(srcPath);
            // создание нового каталога (файла) в указанном каталоге 
            srcFolder.createLink(mainPage.getRepositoryURI(), IFolder.REPLACE, monitor);
            monitor.worked(10);
            project.setDefaultCharset("cp1251", monitor);
        } catch (CoreException x) {
            reportError(x);
        } finally {
            monitor.done();
        }
    }

}
