package com.google.android.finsky.layout.play;

import android.util.SparseArray;
import com.google.android.finsky.utils.FinskyLog;
import java.util.ArrayList;
import java.util.List;

public final class PlayCardClusterRepository
{
  private static final SparseArray<List<PlayCardClusterMetadata>> sClusters16x9;
  private static final SparseArray<List<PlayCardClusterMetadata>> sClusters1x1 = new SparseArray();
  
  static
  {
    sClusters16x9 = new SparseArray();
    ArrayList localArrayList1 = new ArrayList();
    PlayCardClusterMetadata localPlayCardClusterMetadata1 = new PlayCardClusterMetadata(4, 3).addTile(PlayCardClusterMetadata.CARD_SMALL, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 2, 0);
    localPlayCardClusterMetadata1.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata1.mRespectChildHeight = true;
    localArrayList1.add(0, localPlayCardClusterMetadata1);
    PlayCardClusterMetadata localPlayCardClusterMetadata2 = new PlayCardClusterMetadata(4, 3).addTile(PlayCardClusterMetadata.CARD_SMALL, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 2, 0);
    localPlayCardClusterMetadata2.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata2.mRespectChildHeight = true;
    localArrayList1.add(1, localPlayCardClusterMetadata2);
    PlayCardClusterMetadata localPlayCardClusterMetadata3 = new PlayCardClusterMetadata(4, 2).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM, 0, 0);
    localPlayCardClusterMetadata3.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList1.add(2, localPlayCardClusterMetadata3);
    PlayCardClusterMetadata localPlayCardClusterMetadata4 = new PlayCardClusterMetadata(4, 2).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM, 0, 0);
    localPlayCardClusterMetadata4.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList1.add(3, localPlayCardClusterMetadata4);
    PlayCardClusterMetadata localPlayCardClusterMetadata5 = new PlayCardClusterMetadata(4, 3).addTile(PlayCardClusterMetadata.CARD_SMALL, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 2, 0);
    localPlayCardClusterMetadata5.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata5.mRespectChildHeight = true;
    localPlayCardClusterMetadata5.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList1.add(4, localPlayCardClusterMetadata5);
    sClusters1x1.append(0, localArrayList1);
    ArrayList localArrayList2 = new ArrayList();
    PlayCardClusterMetadata localPlayCardClusterMetadata6 = new PlayCardClusterMetadata(6, 3).addTile(PlayCardClusterMetadata.CARD_SMALL, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 2, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0);
    localPlayCardClusterMetadata6.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata6.mRespectChildHeight = true;
    localArrayList2.add(0, localPlayCardClusterMetadata6);
    PlayCardClusterMetadata localPlayCardClusterMetadata7 = new PlayCardClusterMetadata(6, 3).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0);
    localPlayCardClusterMetadata7.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList2.add(1, localPlayCardClusterMetadata7);
    PlayCardClusterMetadata localPlayCardClusterMetadata8 = new PlayCardClusterMetadata(6, 3).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0);
    localPlayCardClusterMetadata8.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList2.add(2, localPlayCardClusterMetadata8);
    PlayCardClusterMetadata localPlayCardClusterMetadata9 = new PlayCardClusterMetadata(6, 3).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0);
    localPlayCardClusterMetadata9.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList2.add(3, localPlayCardClusterMetadata9);
    PlayCardClusterMetadata localPlayCardClusterMetadata10 = new PlayCardClusterMetadata(6, 3).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0);
    localPlayCardClusterMetadata10.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList2.add(3, localPlayCardClusterMetadata10);
    sClusters1x1.append(1, localArrayList2);
    ArrayList localArrayList3 = new ArrayList();
    PlayCardClusterMetadata localPlayCardClusterMetadata11 = new PlayCardClusterMetadata(6, 3).addTile(PlayCardClusterMetadata.CARD_SMALL, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 2, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0);
    localPlayCardClusterMetadata11.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata11.mRespectChildHeight = true;
    localArrayList3.add(0, localPlayCardClusterMetadata11);
    PlayCardClusterMetadata localPlayCardClusterMetadata12 = new PlayCardClusterMetadata(6, 3).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0);
    localPlayCardClusterMetadata12.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList3.add(1, localPlayCardClusterMetadata12);
    PlayCardClusterMetadata localPlayCardClusterMetadata13 = new PlayCardClusterMetadata(6, 6).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 0, 3).addTile(PlayCardClusterMetadata.CARD_SMALL, 2, 3).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 3);
    localPlayCardClusterMetadata13.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList3.add(2, localPlayCardClusterMetadata13);
    PlayCardClusterMetadata localPlayCardClusterMetadata14 = new PlayCardClusterMetadata(6, 6).addTile(PlayCardClusterMetadata.CARD_LARGE, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 3);
    localPlayCardClusterMetadata14.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList3.add(3, localPlayCardClusterMetadata14);
    PlayCardClusterMetadata localPlayCardClusterMetadata15 = new PlayCardClusterMetadata(6, 6).addTile(PlayCardClusterMetadata.CARD_LARGE, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 3);
    localPlayCardClusterMetadata15.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList3.add(3, localPlayCardClusterMetadata15);
    sClusters1x1.append(2, localArrayList3);
    ArrayList localArrayList4 = new ArrayList();
    PlayCardClusterMetadata localPlayCardClusterMetadata16 = new PlayCardClusterMetadata(8, 3).addTile(PlayCardClusterMetadata.CARD_SMALL, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 2, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0);
    localPlayCardClusterMetadata16.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata16.mRespectChildHeight = true;
    localArrayList4.add(0, localPlayCardClusterMetadata16);
    PlayCardClusterMetadata localPlayCardClusterMetadata17 = new PlayCardClusterMetadata(8, 3).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0);
    localPlayCardClusterMetadata17.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList4.add(1, localPlayCardClusterMetadata17);
    PlayCardClusterMetadata localPlayCardClusterMetadata18 = new PlayCardClusterMetadata(8, 3).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0);
    localPlayCardClusterMetadata18.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList4.add(2, localPlayCardClusterMetadata18);
    PlayCardClusterMetadata localPlayCardClusterMetadata19 = new PlayCardClusterMetadata(8, 3).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0);
    localPlayCardClusterMetadata19.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList4.add(3, localPlayCardClusterMetadata19);
    PlayCardClusterMetadata localPlayCardClusterMetadata20 = new PlayCardClusterMetadata(8, 3).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0);
    localPlayCardClusterMetadata20.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList4.add(3, localPlayCardClusterMetadata20);
    sClusters1x1.append(3, localArrayList4);
    ArrayList localArrayList5 = new ArrayList();
    PlayCardClusterMetadata localPlayCardClusterMetadata21 = new PlayCardClusterMetadata(8, 3).addTile(PlayCardClusterMetadata.CARD_SMALL, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 2, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0);
    localPlayCardClusterMetadata21.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata21.mRespectChildHeight = true;
    localArrayList5.add(0, localPlayCardClusterMetadata21);
    PlayCardClusterMetadata localPlayCardClusterMetadata22 = new PlayCardClusterMetadata(8, 3).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0);
    localPlayCardClusterMetadata22.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList5.add(1, localPlayCardClusterMetadata22);
    PlayCardClusterMetadata localPlayCardClusterMetadata23 = new PlayCardClusterMetadata(8, 6).addTile(PlayCardClusterMetadata.CARD_LARGE, 0, 0).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM, 4, 0).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM, 4, 2).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM, 4, 4);
    localPlayCardClusterMetadata23.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList5.add(2, localPlayCardClusterMetadata23);
    PlayCardClusterMetadata localPlayCardClusterMetadata24 = new PlayCardClusterMetadata(8, 6).addTile(PlayCardClusterMetadata.CARD_LARGE, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 3).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 3);
    localPlayCardClusterMetadata24.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList5.add(3, localPlayCardClusterMetadata24);
    PlayCardClusterMetadata localPlayCardClusterMetadata25 = new PlayCardClusterMetadata(8, 8).addTile(PlayCardClusterMetadata.CARD_LARGE, 0, 0).addTile(PlayCardClusterMetadata.CARD_LARGE, 4, 0).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM, 0, 6).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM, 4, 6);
    localPlayCardClusterMetadata25.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList5.add(4, localPlayCardClusterMetadata25);
    sClusters1x1.append(4, localArrayList5);
    ArrayList localArrayList6 = new ArrayList();
    PlayCardClusterMetadata localPlayCardClusterMetadata26 = new PlayCardClusterMetadata(10, 3).addTile(PlayCardClusterMetadata.CARD_SMALL, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 2, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 8, 0);
    localPlayCardClusterMetadata26.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata26.mRespectChildHeight = true;
    localArrayList6.add(0, localPlayCardClusterMetadata26);
    PlayCardClusterMetadata localPlayCardClusterMetadata27 = new PlayCardClusterMetadata(10, 3).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 8, 0);
    localPlayCardClusterMetadata27.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList6.add(1, localPlayCardClusterMetadata27);
    PlayCardClusterMetadata localPlayCardClusterMetadata28 = new PlayCardClusterMetadata(10, 3).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 8, 0);
    localPlayCardClusterMetadata28.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList6.add(2, localPlayCardClusterMetadata28);
    PlayCardClusterMetadata localPlayCardClusterMetadata29 = new PlayCardClusterMetadata(10, 3).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 6, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 8, 0);
    localPlayCardClusterMetadata29.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList6.add(3, localPlayCardClusterMetadata29);
    PlayCardClusterMetadata localPlayCardClusterMetadata30 = new PlayCardClusterMetadata(10, 3).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS, 0, 0).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL, 8, 0);
    localPlayCardClusterMetadata30.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL;
    localArrayList6.add(4, localPlayCardClusterMetadata30);
    sClusters1x1.append(5, localArrayList6);
    ArrayList localArrayList7 = new ArrayList();
    PlayCardClusterMetadata localPlayCardClusterMetadata31 = new PlayCardClusterMetadata(4, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 2, 0);
    localPlayCardClusterMetadata31.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata31.mRespectChildHeight = true;
    localArrayList7.add(0, localPlayCardClusterMetadata31);
    PlayCardClusterMetadata localPlayCardClusterMetadata32 = new PlayCardClusterMetadata(4, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 2, 0);
    localPlayCardClusterMetadata32.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata32.mRespectChildHeight = true;
    localArrayList7.add(1, localPlayCardClusterMetadata32);
    PlayCardClusterMetadata localPlayCardClusterMetadata33 = new PlayCardClusterMetadata(4, 2).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM_16x9, 0, 0);
    localPlayCardClusterMetadata33.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList7.add(2, localPlayCardClusterMetadata33);
    PlayCardClusterMetadata localPlayCardClusterMetadata34 = new PlayCardClusterMetadata(4, 2).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM_16x9, 0, 0);
    localPlayCardClusterMetadata34.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList7.add(3, localPlayCardClusterMetadata34);
    PlayCardClusterMetadata localPlayCardClusterMetadata35 = new PlayCardClusterMetadata(4, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 2, 0);
    localPlayCardClusterMetadata35.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata35.mRespectChildHeight = true;
    localArrayList7.add(4, localPlayCardClusterMetadata35);
    sClusters16x9.append(0, localArrayList7);
    ArrayList localArrayList8 = new ArrayList();
    PlayCardClusterMetadata localPlayCardClusterMetadata36 = new PlayCardClusterMetadata(6, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 2, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0);
    localPlayCardClusterMetadata36.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata36.mRespectChildHeight = true;
    localArrayList8.add(0, localPlayCardClusterMetadata36);
    PlayCardClusterMetadata localPlayCardClusterMetadata37 = new PlayCardClusterMetadata(6, 4).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0);
    localPlayCardClusterMetadata37.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList8.add(1, localPlayCardClusterMetadata37);
    PlayCardClusterMetadata localPlayCardClusterMetadata38 = new PlayCardClusterMetadata(6, 4).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0);
    localPlayCardClusterMetadata38.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList8.add(2, localPlayCardClusterMetadata38);
    PlayCardClusterMetadata localPlayCardClusterMetadata39 = new PlayCardClusterMetadata(6, 4).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0);
    localPlayCardClusterMetadata39.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList8.add(3, localPlayCardClusterMetadata39);
    PlayCardClusterMetadata localPlayCardClusterMetadata40 = new PlayCardClusterMetadata(6, 4).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0);
    localPlayCardClusterMetadata40.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList8.add(4, localPlayCardClusterMetadata40);
    sClusters16x9.append(1, localArrayList8);
    ArrayList localArrayList9 = new ArrayList();
    PlayCardClusterMetadata localPlayCardClusterMetadata41 = new PlayCardClusterMetadata(6, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 2, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0);
    localPlayCardClusterMetadata41.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata41.mRespectChildHeight = true;
    localArrayList9.add(0, localPlayCardClusterMetadata41);
    PlayCardClusterMetadata localPlayCardClusterMetadata42 = new PlayCardClusterMetadata(6, 8).addTile(PlayCardClusterMetadata.CARD_LARGEMINUS_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 0, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 2, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 4);
    localPlayCardClusterMetadata42.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList9.add(1, localPlayCardClusterMetadata42);
    PlayCardClusterMetadata localPlayCardClusterMetadata43 = new PlayCardClusterMetadata(6, 8).addTile(PlayCardClusterMetadata.CARD_LARGEMINUS_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 0, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 2, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 4);
    localPlayCardClusterMetadata43.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList9.add(2, localPlayCardClusterMetadata43);
    PlayCardClusterMetadata localPlayCardClusterMetadata44 = new PlayCardClusterMetadata(6, 8).addTile(PlayCardClusterMetadata.CARD_LARGEMINUS_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 0, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 2, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 4);
    localPlayCardClusterMetadata44.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList9.add(3, localPlayCardClusterMetadata44);
    PlayCardClusterMetadata localPlayCardClusterMetadata45 = new PlayCardClusterMetadata(6, 8).addTile(PlayCardClusterMetadata.CARD_LARGEMINUS_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 0, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 2, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 4);
    localPlayCardClusterMetadata45.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList9.add(4, localPlayCardClusterMetadata45);
    sClusters16x9.append(2, localArrayList9);
    ArrayList localArrayList10 = new ArrayList();
    PlayCardClusterMetadata localPlayCardClusterMetadata46 = new PlayCardClusterMetadata(8, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 2, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 6, 0);
    localPlayCardClusterMetadata46.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata46.mRespectChildHeight = true;
    localArrayList10.add(0, localPlayCardClusterMetadata46);
    PlayCardClusterMetadata localPlayCardClusterMetadata47 = new PlayCardClusterMetadata(8, 4).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 6, 0);
    localPlayCardClusterMetadata47.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList10.add(1, localPlayCardClusterMetadata47);
    PlayCardClusterMetadata localPlayCardClusterMetadata48 = new PlayCardClusterMetadata(8, 4).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 6, 0);
    localPlayCardClusterMetadata48.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList10.add(2, localPlayCardClusterMetadata48);
    PlayCardClusterMetadata localPlayCardClusterMetadata49 = new PlayCardClusterMetadata(8, 4).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 6, 0);
    localPlayCardClusterMetadata49.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList10.add(3, localPlayCardClusterMetadata49);
    PlayCardClusterMetadata localPlayCardClusterMetadata50 = new PlayCardClusterMetadata(8, 4).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 6, 0);
    localPlayCardClusterMetadata50.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList10.add(4, localPlayCardClusterMetadata50);
    sClusters16x9.append(3, localArrayList10);
    ArrayList localArrayList11 = new ArrayList();
    PlayCardClusterMetadata localPlayCardClusterMetadata51 = new PlayCardClusterMetadata(8, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 2, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 6, 0);
    localPlayCardClusterMetadata51.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata51.mRespectChildHeight = true;
    localArrayList11.add(0, localPlayCardClusterMetadata51);
    PlayCardClusterMetadata localPlayCardClusterMetadata52 = new PlayCardClusterMetadata(8, 4).addTile(PlayCardClusterMetadata.CARD_MEDIUMPLUS_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 6, 0);
    localPlayCardClusterMetadata52.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList11.add(1, localPlayCardClusterMetadata52);
    PlayCardClusterMetadata localPlayCardClusterMetadata53 = new PlayCardClusterMetadata(8, 8).addTile(PlayCardClusterMetadata.CARD_LARGE_16x9, 0, 0).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM_16x9, 4, 0).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM_16x9, 4, 2).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM_16x9, 4, 4).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM_16x9, 4, 6);
    localPlayCardClusterMetadata53.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList11.add(2, localPlayCardClusterMetadata53);
    PlayCardClusterMetadata localPlayCardClusterMetadata54 = new PlayCardClusterMetadata(8, 8).addTile(PlayCardClusterMetadata.CARD_LARGE_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 6, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 6, 4);
    localPlayCardClusterMetadata54.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList11.add(3, localPlayCardClusterMetadata54);
    PlayCardClusterMetadata localPlayCardClusterMetadata55 = new PlayCardClusterMetadata(8, 8).addTile(PlayCardClusterMetadata.CARD_LARGE_16x9, 0, 0).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM_16x9, 4, 0).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM_16x9, 4, 2).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM_16x9, 4, 4).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM_16x9, 4, 6);
    localPlayCardClusterMetadata55.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList11.add(4, localPlayCardClusterMetadata55);
    sClusters16x9.append(4, localArrayList11);
    ArrayList localArrayList12 = new ArrayList();
    PlayCardClusterMetadata localPlayCardClusterMetadata56 = new PlayCardClusterMetadata(10, 4).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 2, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 4, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 6, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 8, 0);
    localPlayCardClusterMetadata56.mRespectChildThumbnailAspectRatio = true;
    localPlayCardClusterMetadata56.mRespectChildHeight = true;
    localArrayList12.add(0, localPlayCardClusterMetadata56);
    PlayCardClusterMetadata localPlayCardClusterMetadata57 = new PlayCardClusterMetadata(10, 4).addTile(PlayCardClusterMetadata.CARD_LARGEMINUS_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 6, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 8, 0);
    localPlayCardClusterMetadata57.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList12.add(1, localPlayCardClusterMetadata57);
    PlayCardClusterMetadata localPlayCardClusterMetadata58 = new PlayCardClusterMetadata(10, 4).addTile(PlayCardClusterMetadata.CARD_LARGEMINUS_16x9, 0, 0).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM_16x9, 6, 0).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM_16x9, 6, 2);
    localPlayCardClusterMetadata58.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList12.add(2, localPlayCardClusterMetadata58);
    PlayCardClusterMetadata localPlayCardClusterMetadata59 = new PlayCardClusterMetadata(10, 4).addTile(PlayCardClusterMetadata.CARD_LARGEMINUS_16x9, 0, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 6, 0).addTile(PlayCardClusterMetadata.CARD_SMALL_16x9, 8, 0);
    localPlayCardClusterMetadata59.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList12.add(3, localPlayCardClusterMetadata59);
    PlayCardClusterMetadata localPlayCardClusterMetadata60 = new PlayCardClusterMetadata(10, 4).addTile(PlayCardClusterMetadata.CARD_LARGEMINUS_16x9, 0, 0).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM_16x9, 6, 0).addFlexiTile(PlayCardClusterMetadata.CARD_MEDIUM_16x9, 6, 2);
    localPlayCardClusterMetadata60.mCardMetadataForMinCellHeight = PlayCardClusterMetadata.CARD_SMALL_16x9;
    localArrayList12.add(4, localPlayCardClusterMetadata60);
    sClusters16x9.append(5, localArrayList12);
  }
  
  public static int getConfigurationKey(int paramInt, boolean paramBoolean)
  {
    switch (paramInt)
    {
    default: 
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      FinskyLog.wtf("Unsupported number of columns %d", arrayOfObject);
    case 2: 
      return 0;
    case 3: 
      if (paramBoolean) {
        return 2;
      }
      return 1;
    case 4: 
      if (paramBoolean) {
        return 4;
      }
      return 3;
    }
    return 5;
  }
  
  public static PlayCardClusterMetadata getMetadata(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3)
  {
    int i;
    if (PlayCardClusterMetadata.getAspectRatio(paramInt1) == 1.0F)
    {
      i = 1;
      if (i == 0) {
        break label51;
      }
    }
    label51:
    for (SparseArray localSparseArray = sClusters1x1;; localSparseArray = sClusters16x9)
    {
      return (PlayCardClusterMetadata)((List)localSparseArray.get(getConfigurationKey(paramInt2, paramBoolean))).get(paramInt3);
      i = 0;
      break;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.layout.play.PlayCardClusterRepository
 * JD-Core Version:    0.7.0.1
 */