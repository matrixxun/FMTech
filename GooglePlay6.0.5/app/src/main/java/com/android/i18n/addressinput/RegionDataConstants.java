package com.android.i18n.addressinput;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

final class RegionDataConstants
{
  private static final Map<String, String> COUNTRY_FORMAT_MAP = new HashMap();
  
  static
  {
    for (RegionDataEnum localRegionDataEnum : RegionDataEnum.values()) {
      COUNTRY_FORMAT_MAP.put(localRegionDataEnum.toString(), localRegionDataEnum.jsonString);
    }
  }
  
  static String convertArrayToJsonString(String[] paramArrayOfString)
  {
    JSONObject localJSONObject = new JSONObject();
    int i = 0;
    for (;;)
    {
      if (i < paramArrayOfString.length) {}
      try
      {
        localJSONObject.put(paramArrayOfString[i], paramArrayOfString[(i + 1)]);
        label29:
        i += 2;
        continue;
        return localJSONObject.toString();
      }
      catch (JSONException localJSONException)
      {
        break label29;
      }
    }
  }
  
  static Map<String, String> getCountryFormatMap()
  {
    return COUNTRY_FORMAT_MAP;
  }
  
  private static enum RegionDataEnum
  {
    String jsonString;
    
    static
    {
      RegionDataEnum[] arrayOfRegionDataEnum = new RegionDataEnum['ó'];
      arrayOfRegionDataEnum[0] = AD;
      arrayOfRegionDataEnum[1] = AE;
      arrayOfRegionDataEnum[2] = AF;
      arrayOfRegionDataEnum[3] = AG;
      arrayOfRegionDataEnum[4] = AI;
      arrayOfRegionDataEnum[5] = AL;
      arrayOfRegionDataEnum[6] = AM;
      arrayOfRegionDataEnum[7] = AN;
      arrayOfRegionDataEnum[8] = AO;
      arrayOfRegionDataEnum[9] = AQ;
      arrayOfRegionDataEnum[10] = AR;
      arrayOfRegionDataEnum[11] = AS;
      arrayOfRegionDataEnum[12] = AT;
      arrayOfRegionDataEnum[13] = AU;
      arrayOfRegionDataEnum[14] = AW;
      arrayOfRegionDataEnum[15] = AX;
      arrayOfRegionDataEnum[16] = AZ;
      arrayOfRegionDataEnum[17] = BA;
      arrayOfRegionDataEnum[18] = BB;
      arrayOfRegionDataEnum[19] = BD;
      arrayOfRegionDataEnum[20] = BE;
      arrayOfRegionDataEnum[21] = BF;
      arrayOfRegionDataEnum[22] = BG;
      arrayOfRegionDataEnum[23] = BH;
      arrayOfRegionDataEnum[24] = BI;
      arrayOfRegionDataEnum[25] = BJ;
      arrayOfRegionDataEnum[26] = BL;
      arrayOfRegionDataEnum[27] = BM;
      arrayOfRegionDataEnum[28] = BN;
      arrayOfRegionDataEnum[29] = BO;
      arrayOfRegionDataEnum[30] = BR;
      arrayOfRegionDataEnum[31] = BS;
      arrayOfRegionDataEnum[32] = BT;
      arrayOfRegionDataEnum[33] = BV;
      arrayOfRegionDataEnum[34] = BW;
      arrayOfRegionDataEnum[35] = BY;
      arrayOfRegionDataEnum[36] = BZ;
      arrayOfRegionDataEnum[37] = CA;
      arrayOfRegionDataEnum[38] = CC;
      arrayOfRegionDataEnum[39] = CD;
      arrayOfRegionDataEnum[40] = CF;
      arrayOfRegionDataEnum[41] = CG;
      arrayOfRegionDataEnum[42] = CH;
      arrayOfRegionDataEnum[43] = CI;
      arrayOfRegionDataEnum[44] = CK;
      arrayOfRegionDataEnum[45] = CL;
      arrayOfRegionDataEnum[46] = CM;
      arrayOfRegionDataEnum[47] = CN;
      arrayOfRegionDataEnum[48] = CO;
      arrayOfRegionDataEnum[49] = CR;
      arrayOfRegionDataEnum[50] = CS;
      arrayOfRegionDataEnum[51] = CV;
      arrayOfRegionDataEnum[52] = CX;
      arrayOfRegionDataEnum[53] = CY;
      arrayOfRegionDataEnum[54] = CZ;
      arrayOfRegionDataEnum[55] = DE;
      arrayOfRegionDataEnum[56] = DJ;
      arrayOfRegionDataEnum[57] = DK;
      arrayOfRegionDataEnum[58] = DM;
      arrayOfRegionDataEnum[59] = DO;
      arrayOfRegionDataEnum[60] = DZ;
      arrayOfRegionDataEnum[61] = EC;
      arrayOfRegionDataEnum[62] = EE;
      arrayOfRegionDataEnum[63] = EG;
      arrayOfRegionDataEnum[64] = EH;
      arrayOfRegionDataEnum[65] = ER;
      arrayOfRegionDataEnum[66] = ES;
      arrayOfRegionDataEnum[67] = ET;
      arrayOfRegionDataEnum[68] = FI;
      arrayOfRegionDataEnum[69] = FJ;
      arrayOfRegionDataEnum[70] = FK;
      arrayOfRegionDataEnum[71] = FM;
      arrayOfRegionDataEnum[72] = FO;
      arrayOfRegionDataEnum[73] = FR;
      arrayOfRegionDataEnum[74] = GA;
      arrayOfRegionDataEnum[75] = GB;
      arrayOfRegionDataEnum[76] = GD;
      arrayOfRegionDataEnum[77] = GE;
      arrayOfRegionDataEnum[78] = GF;
      arrayOfRegionDataEnum[79] = GG;
      arrayOfRegionDataEnum[80] = GH;
      arrayOfRegionDataEnum[81] = GI;
      arrayOfRegionDataEnum[82] = GL;
      arrayOfRegionDataEnum[83] = GM;
      arrayOfRegionDataEnum[84] = GN;
      arrayOfRegionDataEnum[85] = GP;
      arrayOfRegionDataEnum[86] = GQ;
      arrayOfRegionDataEnum[87] = GR;
      arrayOfRegionDataEnum[88] = GS;
      arrayOfRegionDataEnum[89] = GT;
      arrayOfRegionDataEnum[90] = GU;
      arrayOfRegionDataEnum[91] = GW;
      arrayOfRegionDataEnum[92] = GY;
      arrayOfRegionDataEnum[93] = HK;
      arrayOfRegionDataEnum[94] = HM;
      arrayOfRegionDataEnum[95] = HN;
      arrayOfRegionDataEnum[96] = HR;
      arrayOfRegionDataEnum[97] = HT;
      arrayOfRegionDataEnum[98] = HU;
      arrayOfRegionDataEnum[99] = ID;
      arrayOfRegionDataEnum[100] = IE;
      arrayOfRegionDataEnum[101] = IL;
      arrayOfRegionDataEnum[102] = IM;
      arrayOfRegionDataEnum[103] = IN;
      arrayOfRegionDataEnum[104] = IO;
      arrayOfRegionDataEnum[105] = IQ;
      arrayOfRegionDataEnum[106] = IS;
      arrayOfRegionDataEnum[107] = IT;
      arrayOfRegionDataEnum[108] = JE;
      arrayOfRegionDataEnum[109] = JM;
      arrayOfRegionDataEnum[110] = JO;
      arrayOfRegionDataEnum[111] = JP;
      arrayOfRegionDataEnum[112] = KE;
      arrayOfRegionDataEnum[113] = KG;
      arrayOfRegionDataEnum[114] = KH;
      arrayOfRegionDataEnum[115] = KI;
      arrayOfRegionDataEnum[116] = KM;
      arrayOfRegionDataEnum[117] = KN;
      arrayOfRegionDataEnum[118] = KR;
      arrayOfRegionDataEnum[119] = KW;
      arrayOfRegionDataEnum[120] = KY;
      arrayOfRegionDataEnum[121] = KZ;
      arrayOfRegionDataEnum[122] = LA;
      arrayOfRegionDataEnum[123] = LB;
      arrayOfRegionDataEnum[124] = LC;
      arrayOfRegionDataEnum[125] = LI;
      arrayOfRegionDataEnum[126] = LK;
      arrayOfRegionDataEnum[127] = LR;
      arrayOfRegionDataEnum[''] = LS;
      arrayOfRegionDataEnum[''] = LT;
      arrayOfRegionDataEnum[''] = LU;
      arrayOfRegionDataEnum[''] = LV;
      arrayOfRegionDataEnum[''] = LY;
      arrayOfRegionDataEnum[''] = MA;
      arrayOfRegionDataEnum[''] = MC;
      arrayOfRegionDataEnum[''] = MD;
      arrayOfRegionDataEnum[''] = ME;
      arrayOfRegionDataEnum[''] = MF;
      arrayOfRegionDataEnum[''] = MG;
      arrayOfRegionDataEnum[''] = MH;
      arrayOfRegionDataEnum[''] = MK;
      arrayOfRegionDataEnum[''] = ML;
      arrayOfRegionDataEnum[''] = MN;
      arrayOfRegionDataEnum[''] = MO;
      arrayOfRegionDataEnum[''] = MP;
      arrayOfRegionDataEnum[''] = MQ;
      arrayOfRegionDataEnum[''] = MR;
      arrayOfRegionDataEnum[''] = MS;
      arrayOfRegionDataEnum[''] = MT;
      arrayOfRegionDataEnum[''] = MU;
      arrayOfRegionDataEnum[''] = MV;
      arrayOfRegionDataEnum[''] = MW;
      arrayOfRegionDataEnum[''] = MX;
      arrayOfRegionDataEnum[''] = MY;
      arrayOfRegionDataEnum[''] = MZ;
      arrayOfRegionDataEnum[''] = NA;
      arrayOfRegionDataEnum[''] = NC;
      arrayOfRegionDataEnum[''] = NE;
      arrayOfRegionDataEnum[''] = NF;
      arrayOfRegionDataEnum[''] = NG;
      arrayOfRegionDataEnum[' '] = NI;
      arrayOfRegionDataEnum['¡'] = NL;
      arrayOfRegionDataEnum['¢'] = NO;
      arrayOfRegionDataEnum['£'] = NP;
      arrayOfRegionDataEnum['¤'] = NR;
      arrayOfRegionDataEnum['¥'] = NU;
      arrayOfRegionDataEnum['¦'] = NZ;
      arrayOfRegionDataEnum['§'] = OM;
      arrayOfRegionDataEnum['¨'] = PA;
      arrayOfRegionDataEnum['©'] = PE;
      arrayOfRegionDataEnum['ª'] = PF;
      arrayOfRegionDataEnum['«'] = PG;
      arrayOfRegionDataEnum['¬'] = PH;
      arrayOfRegionDataEnum['­'] = PK;
      arrayOfRegionDataEnum['®'] = PL;
      arrayOfRegionDataEnum['¯'] = PM;
      arrayOfRegionDataEnum['°'] = PN;
      arrayOfRegionDataEnum['±'] = PR;
      arrayOfRegionDataEnum['²'] = PS;
      arrayOfRegionDataEnum['³'] = PT;
      arrayOfRegionDataEnum['´'] = PW;
      arrayOfRegionDataEnum['µ'] = PY;
      arrayOfRegionDataEnum['¶'] = QA;
      arrayOfRegionDataEnum['·'] = RE;
      arrayOfRegionDataEnum['¸'] = RO;
      arrayOfRegionDataEnum['¹'] = RS;
      arrayOfRegionDataEnum['º'] = RU;
      arrayOfRegionDataEnum['»'] = RW;
      arrayOfRegionDataEnum['¼'] = SA;
      arrayOfRegionDataEnum['½'] = SB;
      arrayOfRegionDataEnum['¾'] = SC;
      arrayOfRegionDataEnum['¿'] = SE;
      arrayOfRegionDataEnum['À'] = SG;
      arrayOfRegionDataEnum['Á'] = SH;
      arrayOfRegionDataEnum['Â'] = SI;
      arrayOfRegionDataEnum['Ã'] = SJ;
      arrayOfRegionDataEnum['Ä'] = SK;
      arrayOfRegionDataEnum['Å'] = SL;
      arrayOfRegionDataEnum['Æ'] = SM;
      arrayOfRegionDataEnum['Ç'] = SN;
      arrayOfRegionDataEnum['È'] = SO;
      arrayOfRegionDataEnum['É'] = SR;
      arrayOfRegionDataEnum['Ê'] = ST;
      arrayOfRegionDataEnum['Ë'] = SV;
      arrayOfRegionDataEnum['Ì'] = SZ;
      arrayOfRegionDataEnum['Í'] = TC;
      arrayOfRegionDataEnum['Î'] = TD;
      arrayOfRegionDataEnum['Ï'] = TF;
      arrayOfRegionDataEnum['Ð'] = TG;
      arrayOfRegionDataEnum['Ñ'] = TH;
      arrayOfRegionDataEnum['Ò'] = TJ;
      arrayOfRegionDataEnum['Ó'] = TK;
      arrayOfRegionDataEnum['Ô'] = TL;
      arrayOfRegionDataEnum['Õ'] = TM;
      arrayOfRegionDataEnum['Ö'] = TN;
      arrayOfRegionDataEnum['×'] = TO;
      arrayOfRegionDataEnum['Ø'] = TR;
      arrayOfRegionDataEnum['Ù'] = TT;
      arrayOfRegionDataEnum['Ú'] = TV;
      arrayOfRegionDataEnum['Û'] = TW;
      arrayOfRegionDataEnum['Ü'] = TZ;
      arrayOfRegionDataEnum['Ý'] = UA;
      arrayOfRegionDataEnum['Þ'] = UG;
      arrayOfRegionDataEnum['ß'] = UM;
      arrayOfRegionDataEnum['à'] = US;
      arrayOfRegionDataEnum['á'] = UY;
      arrayOfRegionDataEnum['â'] = UZ;
      arrayOfRegionDataEnum['ã'] = VA;
      arrayOfRegionDataEnum['ä'] = VC;
      arrayOfRegionDataEnum['å'] = VE;
      arrayOfRegionDataEnum['æ'] = VG;
      arrayOfRegionDataEnum['ç'] = VI;
      arrayOfRegionDataEnum['è'] = VN;
      arrayOfRegionDataEnum['é'] = VU;
      arrayOfRegionDataEnum['ê'] = WF;
      arrayOfRegionDataEnum['ë'] = WS;
      arrayOfRegionDataEnum['ì'] = YE;
      arrayOfRegionDataEnum['í'] = YT;
      arrayOfRegionDataEnum['î'] = YU;
      arrayOfRegionDataEnum['ï'] = ZA;
      arrayOfRegionDataEnum['ð'] = ZM;
      arrayOfRegionDataEnum['ñ'] = ZW;
      arrayOfRegionDataEnum['ò'] = ZZ;
      $VALUES = arrayOfRegionDataEnum;
    }
    
    private RegionDataEnum(String[] paramArrayOfString)
    {
      this.jsonString = RegionDataConstants.convertArrayToJsonString(paramArrayOfString);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.android.i18n.addressinput.RegionDataConstants
 * JD-Core Version:    0.7.0.1
 */