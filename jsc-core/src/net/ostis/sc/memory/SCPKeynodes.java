/*
 * This source file is part of OSTIS (Open Semantic Technology for Intelligent
 * Systems) For the latest info, see http://www.ostis.net
 *
 * Copyright (c) 2011 OSTIS
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
package net.ostis.sc.memory;

/**
 * Keynodes of SCP.
 *
 * @author Dmitry Lazurkin
 */
public class SCPKeynodes extends SCKeynodesBase {

	public static void init(SCSession session) {
		SCKeynodesBase.init(session, SCPKeynodes.class);
	}

	@SegmentURI("/proc/keynode")
	public static SCSegment keynodesSegment;

	@SegmentURI("/proc/scp/core")
	public static SCSegment coreSegment;

	@KeynodeURI("/proc/keynode/scp_process")
	public static SCAddr processSCP;

	@KeynodeURI("/proc/keynode/scp_operator")
	public static SCAddr operatorSCP;

	@KeynodeURI("/proc/keynode/scp_operator_type")
	public static SCAddr operatorTypeSCP;

	@KeynodeURI("/proc/keynode/programSCP")
	public static SCAddr programSCP;

	@KeynodeURI("/proc/keynode/prepared_scp_program")
	public static SCAddr preparedProgramSCP;

	@KeynodeURI("/proc/keynode/const_segments_")
	public static SCAddr const_segments_;

	@KeynodeURI("/proc/keynode/default_seg_")
	public static SCAddr default_seg_;

	@KeynodeURI("/proc/keynode/opened_segments_")
	public static SCAddr opened_segments_;

	@KeynodeURI("/proc/keynode/const_")
	public static SCAddr const_;

	@KeynodeURI("/proc/keynode/var_")
	public static SCAddr var_;

	@KeynodeURI("/proc/keynode/metavar_")
	public static SCAddr metavar_;

	@KeynodeURI("/proc/keynode/pos_")
	public static SCAddr pos_;

	@KeynodeURI("/proc/keynode/neg_")
	public static SCAddr neg_;

	@KeynodeURI("/proc/keynode/fuz_")
	public static SCAddr fuz_;

	@KeynodeURI("/proc/keynode/arc_")
	public static SCAddr arc_;

	@KeynodeURI("/proc/keynode/node_")
	public static SCAddr node_;

	@KeynodeURI("/proc/keynode/undf_")
	public static SCAddr undf_;

	@KeynodeURI("/proc/keynode/permanent_")
	public static SCAddr permanent_;

	@KeynodeURI("/proc/keynode/temporary_")
	public static SCAddr temporary_;

	@KeynodeURI("/proc/keynode/actual_")
	public static SCAddr actual_;

	@KeynodeURI("/proc/keynode/phantom_")
	public static SCAddr phantom_;

	@KeynodeURI("/proc/keynode/typeattrs")
	public static SCAddr typeattrs;

	@KeynodeURI("/proc/keynode/process_state_then")
	public static SCAddr process_state_then;

	@KeynodeURI("/proc/keynode/process_state_else")
	public static SCAddr process_state_else;

	@KeynodeURI("/proc/keynode/process_state_repeat")
	public static SCAddr uprocess_state_repeatndf_;

	@KeynodeURI("/proc/keynode/process_state_sleep")
	public static SCAddr process_state_sleep;

	@KeynodeURI("/proc/keynode/process_state_run")
	public static SCAddr process_state_run;

	@KeynodeURI("/proc/keynode/process_state_dead")
	public static SCAddr process_state_dead;

	@KeynodeURI("/proc/keynode/process_state_error")
	public static SCAddr process_state_error;

	@KeynodeURI("/proc/keynode/process_")
	public static SCAddr process_;

	@KeynodeURI("/proc/keynode/assign_")
	public static SCAddr assign_;

	@KeynodeURI("/proc/keynode/fixed_")
	public static SCAddr fixed_;

	@KeynodeURI("/proc/keynode/f_")
	public static SCAddr f_;

	@KeynodeURI("/proc/keynode/active_")
	public static SCAddr active_;

	@KeynodeURI("/proc/keynode/autosegment_")
	public static SCAddr autosegment_;

	@KeynodeURI("/proc/keynode/sons_garbage_")
	public static SCAddr sons_garbage_;

	@KeynodeURI("/proc/keynode/program_")
	public static SCAddr program_;

	@KeynodeURI("/proc/keynode/then_")
	public static SCAddr then_;

	@KeynodeURI("/proc/keynode/else_")
	public static SCAddr else_;

	@KeynodeURI("/proc/keynode/goto_")
	public static SCAddr goto_;

	@KeynodeURI("/proc/keynode/error_")
	public static SCAddr error_;

	@KeynodeURI("/proc/keynode/init_")
	public static SCAddr init_;

	@KeynodeURI("/proc/keynode/var_value_")
	public static SCAddr var_value_;

	@KeynodeURI("/proc/keynode/real_return_value")
	public static SCAddr real_return_value;

	@KeynodeURI("/proc/keynode/breakpoint")
	public static SCAddr breakpoint;

	@KeynodeURI("/proc/keynode/ptrace_")
	public static SCAddr ptrace_;

	@KeynodeURI("/proc/keynode/ptracer")
	public static SCAddr ptracer;

	@KeynodeURI("/proc/keynode/debugger")
	public static SCAddr debugger;

	@KeynodeURI("/proc/keynode/tracers_")
	public static SCAddr tracers_;

	@KeynodeURI("/proc/keynode/breakpoints_")
	public static SCAddr breakpoints_;

	@KeynodeURI("/proc/keynode/debug_copies_")
	public static SCAddr debug_copies_;

	@KeynodeURI("/proc/keynode/originals_")
	public static SCAddr originals_;

	@KeynodeURI("/proc/keynode/segment_")
	public static SCAddr segment_;

	@KeynodeURI("/proc/keynode/set_")
	public static SCAddr set_;

	@KeynodeURI("/proc/keynode/ev_allow")
	public static SCAddr ev_allow;

	@KeynodeURI("/proc/keynode/ev_deny")
	public static SCAddr ev_deny;

	@KeynodeURI("/proc/keynode/sleeping")
	public static SCAddr sleeping;

	@KeynodeURI("/proc/keynode/wakeup_hook")
	public static SCAddr wakeup_hook;

	@KeynodeURI("/proc/keynode/father_")
	public static SCAddr father_;

	@KeynodeURI("/proc/keynode/child_death_hook_")
	public static SCAddr child_death_hook_;

	@KeynodeURI("/proc/keynode/in_")
	public static SCAddr in_;

	@KeynodeURI("/proc/keynode/out_")
	public static SCAddr out_;

	@KeynodeURI("/proc/keynode/prm_")
	public static SCAddr prm_;

	@KeynodeURI("/proc/keynode/waiting_for_")
	public static SCAddr waiting_for_;

	@KeynodeURI("/proc/keynode/All_childs")
	public static SCAddr All_childs;

	@KeynodeURI("/proc/keynode/output_prm_binding_")
	public static SCAddr output_prm_binding_;

	@KeynodeURI("/proc/keynode/wait_gen_output_arc")
	public static SCAddr wait_gen_output_arc;

	@KeynodeURI("/proc/keynode/wait_gen_input_arc")
	public static SCAddr wait_gen_input_arc;

	@KeynodeURI("/proc/keynode/wait_input_arc")
	public static SCAddr wait_input_arc;

	@KeynodeURI("/proc/keynode/wait_output_arc")
	public static SCAddr wait_output_arc;

	@KeynodeURI("/proc/keynode/wait_erase_element")
	public static SCAddr wait_erase_element;

	@KeynodeURI("/proc/keynode/wait_gen_output_arc_pre")
	public static SCAddr wait_gen_output_arc_pre;

	@KeynodeURI("/proc/keynode/wait_gen_input_arc_pre")
	public static SCAddr wait_gen_input_arc_pre;

	@KeynodeURI("/proc/keynode/wait_erase_element_pre")
	public static SCAddr wait_erase_element_pre;

	@KeynodeURI("/proc/keynode/wait_recieve_message")
	public static SCAddr wait_recieve_message;

	@KeynodeURI("/proc/keynode/var_value_")
	public static SCAddr varValue_;

	@KeynodeURI("/proc/keynode/true")
	public static SCAddr True;

	@KeynodeURI("/proc/keynode/false")
	public static SCAddr False;

	@KeynodeURI("/proc/keynode/operator_file_")
	public static SCAddr operator_file_;

	@KeynodeURI("/proc/keynode/operator_line_")
	public static SCAddr operator_line_;

	@KeynodeURI("/proc/scp/keynode/processStack_")
	public static SCAddr processStack_;

	@KeynodesNumberPatternURI(patternURI = "/proc/keynode/segc_{0}_", patternName = "segc_{0}_", startIndex = 0, endIndex = 10)
	public static SCAddr[] numberSegcAttrs;

	public static SCAddr segc_0_;
	public static SCAddr segc_1_;
	public static SCAddr segc_2_;
	public static SCAddr segc_3_;
	public static SCAddr segc_4_;
	public static SCAddr segc_5_;
	public static SCAddr segc_6_;
	public static SCAddr segc_7_;
	public static SCAddr segc_8_;
	public static SCAddr segc_9_;
	public static SCAddr segc_10_;
	public static SCAddr segc_11_;
	public static SCAddr segc_12_;
	public static SCAddr segc_13_;
	public static SCAddr segc_14_;
	public static SCAddr segc_15_;
	public static SCAddr segc_16_;
	public static SCAddr segc_17_;
	public static SCAddr segc_18_;
	public static SCAddr segc_19_;
	public static SCAddr segc_20_;
	public static SCAddr segc_21_;
	public static SCAddr segc_22_;
	public static SCAddr segc_23_;
	public static SCAddr segc_24_;
	public static SCAddr segc_25_;
	public static SCAddr segc_26_;
	public static SCAddr segc_27_;
	public static SCAddr segc_28_;
	public static SCAddr segc_29_;
	public static SCAddr segc_30_;
	public static SCAddr segc_31_;
	public static SCAddr segc_32_;
	public static SCAddr segc_33_;
	public static SCAddr segc_34_;
	public static SCAddr segc_35_;
	public static SCAddr segc_36_;
	public static SCAddr segc_37_;
	public static SCAddr segc_38_;
	public static SCAddr segc_39_;
	public static SCAddr segc_40_;
	public static SCAddr segc_41_;
	public static SCAddr segc_42_;
	public static SCAddr segc_43_;
	public static SCAddr segc_44_;
	public static SCAddr segc_45_;
	public static SCAddr segc_46_;
	public static SCAddr segc_47_;
	public static SCAddr segc_48_;
	public static SCAddr segc_49_;
	public static SCAddr segc_50_;
	public static SCAddr segc_51_;
	public static SCAddr segc_52_;
	public static SCAddr segc_53_;
	public static SCAddr segc_54_;
	public static SCAddr segc_55_;
	public static SCAddr segc_56_;
	public static SCAddr segc_57_;
	public static SCAddr segc_58_;
	public static SCAddr segc_59_;
	public static SCAddr segc_60_;
	public static SCAddr segc_61_;
	public static SCAddr segc_62_;
	public static SCAddr segc_63_;
	public static SCAddr segc_64_;
	public static SCAddr segc_65_;
	public static SCAddr segc_66_;
	public static SCAddr segc_67_;
	public static SCAddr segc_68_;
	public static SCAddr segc_69_;
	public static SCAddr segc_70_;
	public static SCAddr segc_71_;
	public static SCAddr segc_72_;
	public static SCAddr segc_73_;
	public static SCAddr segc_74_;
	public static SCAddr segc_75_;
	public static SCAddr segc_76_;
	public static SCAddr segc_77_;
	public static SCAddr segc_78_;
	public static SCAddr segc_79_;
	public static SCAddr segc_80_;
	public static SCAddr segc_81_;
	public static SCAddr segc_82_;
	public static SCAddr segc_83_;
	public static SCAddr segc_84_;
	public static SCAddr segc_85_;
	public static SCAddr segc_86_;
	public static SCAddr segc_87_;
	public static SCAddr segc_88_;
	public static SCAddr segc_89_;
	public static SCAddr segc_90_;
	public static SCAddr segc_91_;
	public static SCAddr segc_92_;
	public static SCAddr segc_93_;
	public static SCAddr segc_94_;
	public static SCAddr segc_95_;
	public static SCAddr segc_96_;
	public static SCAddr segc_97_;
	public static SCAddr segc_98_;
	public static SCAddr segc_99_;
	public static SCAddr segc_100_;
	public static SCAddr segc_101_;
	public static SCAddr segc_102_;
	public static SCAddr segc_103_;
	public static SCAddr segc_104_;
	public static SCAddr segc_105_;
	public static SCAddr segc_106_;
	public static SCAddr segc_107_;
	public static SCAddr segc_108_;
	public static SCAddr segc_109_;
	public static SCAddr segc_110_;
	public static SCAddr segc_111_;
	public static SCAddr segc_112_;
	public static SCAddr segc_113_;
	public static SCAddr segc_114_;
	public static SCAddr segc_115_;
	public static SCAddr segc_116_;
	public static SCAddr segc_117_;
	public static SCAddr segc_118_;
	public static SCAddr segc_119_;
	public static SCAddr segc_120_;
	public static SCAddr segc_121_;
	public static SCAddr segc_122_;
	public static SCAddr segc_123_;
	public static SCAddr segc_124_;
	public static SCAddr segc_125_;
	public static SCAddr segc_126_;
	public static SCAddr segc_127_;
	public static SCAddr segc_128_;
	public static SCAddr segc_129_;
	public static SCAddr segc_130_;
	public static SCAddr segc_131_;
	public static SCAddr segc_132_;
	public static SCAddr segc_133_;
	public static SCAddr segc_134_;
	public static SCAddr segc_135_;
	public static SCAddr segc_136_;
	public static SCAddr segc_137_;
	public static SCAddr segc_138_;
	public static SCAddr segc_139_;
	public static SCAddr segc_140_;
	public static SCAddr segc_141_;
	public static SCAddr segc_142_;
	public static SCAddr segc_143_;
	public static SCAddr segc_144_;
	public static SCAddr segc_145_;
	public static SCAddr segc_146_;
	public static SCAddr segc_147_;
	public static SCAddr segc_148_;
	public static SCAddr segc_149_;
	public static SCAddr segc_150_;
	public static SCAddr segc_151_;
	public static SCAddr segc_152_;
	public static SCAddr segc_153_;
	public static SCAddr segc_154_;
	public static SCAddr segc_155_;
	public static SCAddr segc_156_;
	public static SCAddr segc_157_;
	public static SCAddr segc_158_;
	public static SCAddr segc_159_;
	public static SCAddr segc_160_;
	public static SCAddr segc_161_;
	public static SCAddr segc_162_;
	public static SCAddr segc_163_;
	public static SCAddr segc_164_;
	public static SCAddr segc_165_;
	public static SCAddr segc_166_;
	public static SCAddr segc_167_;
	public static SCAddr segc_168_;
	public static SCAddr segc_169_;
	public static SCAddr segc_170_;
	public static SCAddr segc_171_;
	public static SCAddr segc_172_;
	public static SCAddr segc_173_;
	public static SCAddr segc_174_;
	public static SCAddr segc_175_;
	public static SCAddr segc_176_;
	public static SCAddr segc_177_;
	public static SCAddr segc_178_;
	public static SCAddr segc_179_;
	public static SCAddr segc_180_;
	public static SCAddr segc_181_;
	public static SCAddr segc_182_;
	public static SCAddr segc_183_;
	public static SCAddr segc_184_;
	public static SCAddr segc_185_;
	public static SCAddr segc_186_;
	public static SCAddr segc_187_;
	public static SCAddr segc_188_;
	public static SCAddr segc_189_;
	public static SCAddr segc_190_;
	public static SCAddr segc_191_;
	public static SCAddr segc_192_;
	public static SCAddr segc_193_;
	public static SCAddr segc_194_;
	public static SCAddr segc_195_;
	public static SCAddr segc_196_;
	public static SCAddr segc_197_;
	public static SCAddr segc_198_;
	public static SCAddr segc_199_;
	public static SCAddr segc_200_;
	public static SCAddr segc_201_;
	public static SCAddr segc_202_;
	public static SCAddr segc_203_;
	public static SCAddr segc_204_;
	public static SCAddr segc_205_;
	public static SCAddr segc_206_;
	public static SCAddr segc_207_;
	public static SCAddr segc_208_;
	public static SCAddr segc_209_;
	public static SCAddr segc_210_;
	public static SCAddr segc_211_;
	public static SCAddr segc_212_;
	public static SCAddr segc_213_;
	public static SCAddr segc_214_;
	public static SCAddr segc_215_;
	public static SCAddr segc_216_;
	public static SCAddr segc_217_;
	public static SCAddr segc_218_;
	public static SCAddr segc_219_;
	public static SCAddr segc_220_;
	public static SCAddr segc_221_;
	public static SCAddr segc_222_;
	public static SCAddr segc_223_;
	public static SCAddr segc_224_;
	public static SCAddr segc_225_;
	public static SCAddr segc_226_;
	public static SCAddr segc_227_;
	public static SCAddr segc_228_;
	public static SCAddr segc_229_;
	public static SCAddr segc_230_;
	public static SCAddr segc_231_;
	public static SCAddr segc_232_;
	public static SCAddr segc_233_;
	public static SCAddr segc_234_;
	public static SCAddr segc_235_;
	public static SCAddr segc_236_;
	public static SCAddr segc_237_;
	public static SCAddr segc_238_;
	public static SCAddr segc_239_;
	public static SCAddr segc_240_;
	public static SCAddr segc_241_;
	public static SCAddr segc_242_;
	public static SCAddr segc_243_;
	public static SCAddr segc_244_;
	public static SCAddr segc_245_;
	public static SCAddr segc_246_;
	public static SCAddr segc_247_;
	public static SCAddr segc_248_;
	public static SCAddr segc_249_;
	public static SCAddr segc_250_;
	public static SCAddr segc_251_;
	public static SCAddr segc_252_;
	public static SCAddr segc_253_;
	public static SCAddr segc_254_;
	public static SCAddr segc_255_;

	@KeynodesNumberPatternURI(patternURI = "/proc/keynode/set{0}_", patternName = "set{0}_", startIndex = 0, endIndex = 10)
	public static SCAddr[] numberSetAttrs;

	public static SCAddr set0_;
	public static SCAddr set1_;
	public static SCAddr set2_;
	public static SCAddr set3_;
	public static SCAddr set4_;
	public static SCAddr set5_;
	public static SCAddr set6_;
	public static SCAddr set7_;
	public static SCAddr set8_;
	public static SCAddr set9_;
	public static SCAddr set10_;
	public static SCAddr set11_;
	public static SCAddr set12_;
	public static SCAddr set13_;
	public static SCAddr set14_;
	public static SCAddr set15_;
	public static SCAddr set16_;
	public static SCAddr set17_;
	public static SCAddr set18_;
	public static SCAddr set19_;
	public static SCAddr set20_;
	public static SCAddr set21_;
	public static SCAddr set22_;
	public static SCAddr set23_;
	public static SCAddr set24_;
	public static SCAddr set25_;
	public static SCAddr set26_;
	public static SCAddr set27_;
	public static SCAddr set28_;
	public static SCAddr set29_;
	public static SCAddr set30_;
	public static SCAddr set31_;
	public static SCAddr set32_;
	public static SCAddr set33_;
	public static SCAddr set34_;
	public static SCAddr set35_;
	public static SCAddr set36_;
	public static SCAddr set37_;
	public static SCAddr set38_;
	public static SCAddr set39_;
	public static SCAddr set40_;
	public static SCAddr set41_;
	public static SCAddr set42_;
	public static SCAddr set43_;
	public static SCAddr set44_;
	public static SCAddr set45_;
	public static SCAddr set46_;
	public static SCAddr set47_;
	public static SCAddr set48_;
	public static SCAddr set49_;
	public static SCAddr set50_;
	public static SCAddr set51_;
	public static SCAddr set52_;
	public static SCAddr set53_;
	public static SCAddr set54_;
	public static SCAddr set55_;
	public static SCAddr set56_;
	public static SCAddr set57_;
	public static SCAddr set58_;
	public static SCAddr set59_;
	public static SCAddr set60_;
	public static SCAddr set61_;
	public static SCAddr set62_;
	public static SCAddr set63_;
	public static SCAddr set64_;
	public static SCAddr set65_;
	public static SCAddr set66_;
	public static SCAddr set67_;
	public static SCAddr set68_;
	public static SCAddr set69_;
	public static SCAddr set70_;
	public static SCAddr set71_;
	public static SCAddr set72_;
	public static SCAddr set73_;
	public static SCAddr set74_;
	public static SCAddr set75_;
	public static SCAddr set76_;
	public static SCAddr set77_;
	public static SCAddr set78_;
	public static SCAddr set79_;
	public static SCAddr set80_;
	public static SCAddr set81_;
	public static SCAddr set82_;
	public static SCAddr set83_;
	public static SCAddr set84_;
	public static SCAddr set85_;
	public static SCAddr set86_;
	public static SCAddr set87_;
	public static SCAddr set88_;
	public static SCAddr set89_;
	public static SCAddr set90_;
	public static SCAddr set91_;
	public static SCAddr set92_;
	public static SCAddr set93_;
	public static SCAddr set94_;
	public static SCAddr set95_;
	public static SCAddr set96_;
	public static SCAddr set97_;
	public static SCAddr set98_;
	public static SCAddr set99_;
	public static SCAddr set100_;
	public static SCAddr set101_;
	public static SCAddr set102_;
	public static SCAddr set103_;
	public static SCAddr set104_;
	public static SCAddr set105_;
	public static SCAddr set106_;
	public static SCAddr set107_;
	public static SCAddr set108_;
	public static SCAddr set109_;
	public static SCAddr set110_;
	public static SCAddr set111_;
	public static SCAddr set112_;
	public static SCAddr set113_;
	public static SCAddr set114_;
	public static SCAddr set115_;
	public static SCAddr set116_;
	public static SCAddr set117_;
	public static SCAddr set118_;
	public static SCAddr set119_;
	public static SCAddr set120_;
	public static SCAddr set121_;
	public static SCAddr set122_;
	public static SCAddr set123_;
	public static SCAddr set124_;
	public static SCAddr set125_;
	public static SCAddr set126_;
	public static SCAddr set127_;
	public static SCAddr set128_;
	public static SCAddr set129_;
	public static SCAddr set130_;
	public static SCAddr set131_;
	public static SCAddr set132_;
	public static SCAddr set133_;
	public static SCAddr set134_;
	public static SCAddr set135_;
	public static SCAddr set136_;
	public static SCAddr set137_;
	public static SCAddr set138_;
	public static SCAddr set139_;
	public static SCAddr set140_;
	public static SCAddr set141_;
	public static SCAddr set142_;
	public static SCAddr set143_;
	public static SCAddr set144_;
	public static SCAddr set145_;
	public static SCAddr set146_;
	public static SCAddr set147_;
	public static SCAddr set148_;
	public static SCAddr set149_;
	public static SCAddr set150_;
	public static SCAddr set151_;
	public static SCAddr set152_;
	public static SCAddr set153_;
	public static SCAddr set154_;
	public static SCAddr set155_;
	public static SCAddr set156_;
	public static SCAddr set157_;
	public static SCAddr set158_;
	public static SCAddr set159_;
	public static SCAddr set160_;
	public static SCAddr set161_;
	public static SCAddr set162_;
	public static SCAddr set163_;
	public static SCAddr set164_;
	public static SCAddr set165_;
	public static SCAddr set166_;
	public static SCAddr set167_;
	public static SCAddr set168_;
	public static SCAddr set169_;
	public static SCAddr set170_;
	public static SCAddr set171_;
	public static SCAddr set172_;
	public static SCAddr set173_;
	public static SCAddr set174_;
	public static SCAddr set175_;
	public static SCAddr set176_;
	public static SCAddr set177_;
	public static SCAddr set178_;
	public static SCAddr set179_;
	public static SCAddr set180_;
	public static SCAddr set181_;
	public static SCAddr set182_;
	public static SCAddr set183_;
	public static SCAddr set184_;
	public static SCAddr set185_;
	public static SCAddr set186_;
	public static SCAddr set187_;
	public static SCAddr set188_;
	public static SCAddr set189_;
	public static SCAddr set190_;
	public static SCAddr set191_;
	public static SCAddr set192_;
	public static SCAddr set193_;
	public static SCAddr set194_;
	public static SCAddr set195_;
	public static SCAddr set196_;
	public static SCAddr set197_;
	public static SCAddr set198_;
	public static SCAddr set199_;
	public static SCAddr set200_;
	public static SCAddr set201_;
	public static SCAddr set202_;
	public static SCAddr set203_;
	public static SCAddr set204_;
	public static SCAddr set205_;
	public static SCAddr set206_;
	public static SCAddr set207_;
	public static SCAddr set208_;
	public static SCAddr set209_;
	public static SCAddr set210_;
	public static SCAddr set211_;
	public static SCAddr set212_;
	public static SCAddr set213_;
	public static SCAddr set214_;
	public static SCAddr set215_;
	public static SCAddr set216_;
	public static SCAddr set217_;
	public static SCAddr set218_;
	public static SCAddr set219_;
	public static SCAddr set220_;
	public static SCAddr set221_;
	public static SCAddr set222_;
	public static SCAddr set223_;
	public static SCAddr set224_;
	public static SCAddr set225_;
	public static SCAddr set226_;
	public static SCAddr set227_;
	public static SCAddr set228_;
	public static SCAddr set229_;
	public static SCAddr set230_;
	public static SCAddr set231_;
	public static SCAddr set232_;
	public static SCAddr set233_;
	public static SCAddr set234_;
	public static SCAddr set235_;
	public static SCAddr set236_;
	public static SCAddr set237_;
	public static SCAddr set238_;
	public static SCAddr set239_;
	public static SCAddr set240_;
	public static SCAddr set241_;
	public static SCAddr set242_;
	public static SCAddr set243_;
	public static SCAddr set244_;
	public static SCAddr set245_;
	public static SCAddr set246_;
	public static SCAddr set247_;
	public static SCAddr set248_;
	public static SCAddr set249_;
	public static SCAddr set250_;
	public static SCAddr set251_;
	public static SCAddr set252_;
	public static SCAddr set253_;
	public static SCAddr set254_;
	public static SCAddr set255_;
}
