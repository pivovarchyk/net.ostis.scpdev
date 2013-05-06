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
package net.ostis.scpdev.m4scp.editors;

import net.ostis.scpdev.editors.ColorManager;
import net.ostis.scpdev.editors.IScTextEditor;
import net.ostis.scpdev.editors.scs.SCsDocumentProvider;

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.ui.editors.text.TextEditor;

/**
 * @author Dmitry Lazurkin
 */
public class M4ScpSourceEditor extends TextEditor implements IScTextEditor {
    private ColorManager colorManager;

    public M4ScpSourceEditor() {
        super();
        colorManager = new ColorManager();
        setSourceViewerConfiguration(new M4ScpSourceViewerConfiguration(colorManager));
        setDocumentProvider(new SCsDocumentProvider());
    }

    public void dispose() {
        colorManager.dispose();
        super.dispose();
    }

    @Override
    public ISourceViewer getScSourceViewer() {
        return getSourceViewer();
    }
    
    @Override
    public IVerticalRuler getScVerticalRuler()
    {
    	return getVerticalRuler();
    }

}
