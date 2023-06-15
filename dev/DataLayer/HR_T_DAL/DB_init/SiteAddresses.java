package DataLayer.HR_T_DAL.DB_init;
//static class for site addresses
// 2 static methods:
//1 get i and return the address of the i'th branch
//2 get i and return the address of the i'th supplier
public class SiteAddresses {
    public static String getBranchAddress(int i){
        switch (i){
            case 0:
                return "רמי לוי, הע״ל, באר שבע";
            case 1:
                return "רמי לוי בשכונה, יעקב כהן, באר שבע";
            case 2:
                return "רמי לוי שיווק השקמה באר טוביה";
            case 3:
                return "רמי לוי נתיבות, בית הגדי";
            case 4:
                return "רמי לוי העמל, בת ים";
            case 5:
                return "רמי לוי אשקלון צה״ל 99, צה״ל, אשקלון";
            case 6:
                return "רמי לוי - סניף עטרות";
            case 7:
                return "רמי לוי קרית גת";
            case 8:
                return "רמי לוי אילת";
            default:
                return "רמי לוי העמל, בת ים";
        }
    }
    public static String getSupplierAddress(int i){
        switch (i){
            case 0:
                return "Tnuva, Rehovot";
            case 1:
                return "Coca-Cola, Bnei Brak";
            case 2:
                return "Fish Market, Tel Aviv-Yafo";
            case 3:
                return "Happy Market, Efrayim Kishon St 9, Be'er Ya'akov";
            case 4:
                return "Vegetables store, HaMa'avak St 43, Giv'atayim";
            case 5:
                return "Bana drinks, Ha-Manof St 8, Rehovot";
            case 6:
                return "Deli place, Lehavim";
            case 7:
                return "Dragon Chinese Supermarket, Rosh Pina St 6, Tel Aviv-Yafo";
            case 8:
                return "Vaks Coffee, Derech Yitshak Rabin 2, Bet Shemesh";
            default:
                return "Tnuva Rehovot";
        }
    }
}