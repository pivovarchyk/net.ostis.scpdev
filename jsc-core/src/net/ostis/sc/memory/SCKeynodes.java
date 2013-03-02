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
 * WARRANT; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with OSTIS. If not, see <http://www.gnu.org/licenses/>.
 */
package net.ostis.sc.memory;

/**
 * SC-core keynodes: - /info/dirent - /proc/keynode/1_, /proc/keynode/2_ and etc
 *
 * @author Dmitry Lazurkin
 */
public class SCKeynodes extends SCKeynodesBase {

	public static void init(SCSession session) {
		SCKeynodesBase.init(session, SCKeynodes.class);
	}

	@KeynodeURI("/info/dirent")
	public static SCAddr dirent;

	@KeynodeURI("/proc/keynode/list")
	public static SCAddr list;
	@KeynodeURI("/proc/keynode/list_next_")
	public static SCAddr list_next_;
	@KeynodeURI("/proc/keynode/list_value_")
	public static SCAddr list_value_;

	@KeynodesNumberPatternURI(patternURI = "/proc/keynode/{0}_", patternName = "n{0}_", startIndex = 0, endIndex = 10)
	public static SCAddr[] numberAttrs;

	public static SCAddr n0_;
	public static SCAddr n1_;
	public static SCAddr n2_;
	public static SCAddr n3_;
	public static SCAddr n4_;
	public static SCAddr n5_;
	public static SCAddr n6_;
	public static SCAddr n7_;
	public static SCAddr n8_;
	public static SCAddr n9_;
	public static SCAddr n10_;
	public static SCAddr n11_;
	public static SCAddr n12_;
	public static SCAddr n13_;
	public static SCAddr n14_;
	public static SCAddr n15_;
	public static SCAddr n16_;
	public static SCAddr n17_;
	public static SCAddr n18_;
	public static SCAddr n19_;
	public static SCAddr n20_;
	public static SCAddr n21_;
	public static SCAddr n22_;
	public static SCAddr n23_;
	public static SCAddr n24_;
	public static SCAddr n25_;
	public static SCAddr n26_;
	public static SCAddr n27_;
	public static SCAddr n28_;
	public static SCAddr n29_;
	public static SCAddr n30_;
	public static SCAddr n31_;
	public static SCAddr n32_;
	public static SCAddr n33_;
	public static SCAddr n34_;
	public static SCAddr n35_;
	public static SCAddr n36_;
	public static SCAddr n37_;
	public static SCAddr n38_;
	public static SCAddr n39_;
	public static SCAddr n40_;
	public static SCAddr n41_;
	public static SCAddr n42_;
	public static SCAddr n43_;
	public static SCAddr n44_;
	public static SCAddr n45_;
	public static SCAddr n46_;
	public static SCAddr n47_;
	public static SCAddr n48_;
	public static SCAddr n49_;
	public static SCAddr n50_;
	public static SCAddr n51_;
	public static SCAddr n52_;
	public static SCAddr n53_;
	public static SCAddr n54_;
	public static SCAddr n55_;
	public static SCAddr n56_;
	public static SCAddr n57_;
	public static SCAddr n58_;
	public static SCAddr n59_;
	public static SCAddr n60_;
	public static SCAddr n61_;
	public static SCAddr n62_;
	public static SCAddr n63_;
	public static SCAddr n64_;
	public static SCAddr n65_;
	public static SCAddr n66_;
	public static SCAddr n67_;
	public static SCAddr n68_;
	public static SCAddr n69_;
	public static SCAddr n70_;
	public static SCAddr n71_;
	public static SCAddr n72_;
	public static SCAddr n73_;
	public static SCAddr n74_;
	public static SCAddr n75_;
	public static SCAddr n76_;
	public static SCAddr n77_;
	public static SCAddr n78_;
	public static SCAddr n79_;
	public static SCAddr n80_;
	public static SCAddr n81_;
	public static SCAddr n82_;
	public static SCAddr n83_;
	public static SCAddr n84_;
	public static SCAddr n85_;
	public static SCAddr n86_;
	public static SCAddr n87_;
	public static SCAddr n88_;
	public static SCAddr n89_;
	public static SCAddr n90_;
	public static SCAddr n91_;
	public static SCAddr n92_;
	public static SCAddr n93_;
	public static SCAddr n94_;
	public static SCAddr n95_;
	public static SCAddr n96_;
	public static SCAddr n97_;
	public static SCAddr n98_;
	public static SCAddr n99_;
	public static SCAddr n100_;
	public static SCAddr n101_;
	public static SCAddr n102_;
	public static SCAddr n103_;
	public static SCAddr n104_;
	public static SCAddr n105_;
	public static SCAddr n106_;
	public static SCAddr n107_;
	public static SCAddr n108_;
	public static SCAddr n109_;
	public static SCAddr n110_;
	public static SCAddr n111_;
	public static SCAddr n112_;
	public static SCAddr n113_;
	public static SCAddr n114_;
	public static SCAddr n115_;
	public static SCAddr n116_;
	public static SCAddr n117_;
	public static SCAddr n118_;
	public static SCAddr n119_;
	public static SCAddr n120_;
	public static SCAddr n121_;
	public static SCAddr n122_;
	public static SCAddr n123_;
	public static SCAddr n124_;
	public static SCAddr n125_;
	public static SCAddr n126_;
	public static SCAddr n127_;
	public static SCAddr n128_;
	public static SCAddr n129_;
	public static SCAddr n130_;
	public static SCAddr n131_;
	public static SCAddr n132_;
	public static SCAddr n133_;
	public static SCAddr n134_;
	public static SCAddr n135_;
	public static SCAddr n136_;
	public static SCAddr n137_;
	public static SCAddr n138_;
	public static SCAddr n139_;
	public static SCAddr n140_;
	public static SCAddr n141_;
	public static SCAddr n142_;
	public static SCAddr n143_;
	public static SCAddr n144_;
	public static SCAddr n145_;
	public static SCAddr n146_;
	public static SCAddr n147_;
	public static SCAddr n148_;
	public static SCAddr n149_;
	public static SCAddr n150_;
	public static SCAddr n151_;
	public static SCAddr n152_;
	public static SCAddr n153_;
	public static SCAddr n154_;
	public static SCAddr n155_;
	public static SCAddr n156_;
	public static SCAddr n157_;
	public static SCAddr n158_;
	public static SCAddr n159_;
	public static SCAddr n160_;
	public static SCAddr n161_;
	public static SCAddr n162_;
	public static SCAddr n163_;
	public static SCAddr n164_;
	public static SCAddr n165_;
	public static SCAddr n166_;
	public static SCAddr n167_;
	public static SCAddr n168_;
	public static SCAddr n169_;
	public static SCAddr n170_;
	public static SCAddr n171_;
	public static SCAddr n172_;
	public static SCAddr n173_;
	public static SCAddr n174_;
	public static SCAddr n175_;
	public static SCAddr n176_;
	public static SCAddr n177_;
	public static SCAddr n178_;
	public static SCAddr n179_;
	public static SCAddr n180_;
	public static SCAddr n181_;
	public static SCAddr n182_;
	public static SCAddr n183_;
	public static SCAddr n184_;
	public static SCAddr n185_;
	public static SCAddr n186_;
	public static SCAddr n187_;
	public static SCAddr n188_;
	public static SCAddr n189_;
	public static SCAddr n190_;
	public static SCAddr n191_;
	public static SCAddr n192_;
	public static SCAddr n193_;
	public static SCAddr n194_;
	public static SCAddr n195_;
	public static SCAddr n196_;
	public static SCAddr n197_;
	public static SCAddr n198_;
	public static SCAddr n199_;
	public static SCAddr n200_;
	public static SCAddr n201_;
	public static SCAddr n202_;
	public static SCAddr n203_;
	public static SCAddr n204_;
	public static SCAddr n205_;
	public static SCAddr n206_;
	public static SCAddr n207_;
	public static SCAddr n208_;
	public static SCAddr n209_;
	public static SCAddr n210_;
	public static SCAddr n211_;
	public static SCAddr n212_;
	public static SCAddr n213_;
	public static SCAddr n214_;
	public static SCAddr n215_;
	public static SCAddr n216_;
	public static SCAddr n217_;
	public static SCAddr n218_;
	public static SCAddr n219_;
	public static SCAddr n220_;
	public static SCAddr n221_;
	public static SCAddr n222_;
	public static SCAddr n223_;
	public static SCAddr n224_;
	public static SCAddr n225_;
	public static SCAddr n226_;
	public static SCAddr n227_;
	public static SCAddr n228_;
	public static SCAddr n229_;
	public static SCAddr n230_;
	public static SCAddr n231_;
	public static SCAddr n232_;
	public static SCAddr n233_;
	public static SCAddr n234_;
	public static SCAddr n235_;
	public static SCAddr n236_;
	public static SCAddr n237_;
	public static SCAddr n238_;
	public static SCAddr n239_;
	public static SCAddr n240_;
	public static SCAddr n241_;
	public static SCAddr n242_;
	public static SCAddr n243_;
	public static SCAddr n244_;
	public static SCAddr n245_;
	public static SCAddr n246_;
	public static SCAddr n247_;
	public static SCAddr n248_;
	public static SCAddr n249_;
	public static SCAddr n250_;
	public static SCAddr n251_;
	public static SCAddr n252_;
	public static SCAddr n253_;
	public static SCAddr n254_;
	public static SCAddr n255_;
}
