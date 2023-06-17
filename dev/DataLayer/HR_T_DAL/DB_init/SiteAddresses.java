package DataLayer.HR_T_DAL.DB_init;

import java.util.HashMap;
import java.util.Map;

//static class for site addresses
// 2 static methods:
//1 get i and return the address of the i'th branch
//2 get i and return the address of the i'th supplier
public class SiteAddresses {




    public static Map <String,String> supplierAddressMap;

    public static String getBranchAddress(int i){
        switch (i){
            case 0:
                return "Rami Levy, Beit HaGadi";
            case 1:
                return "Rami Levy Supermarket, HaMeshek Street, Beer Sheva";
            case 2:
                return "Rami Levy, Derekh ha-Darom, Kiryat Gat";
            case 3:
                return "Rami Levy, Ha-Metakhnen Street, Ashkelon";
            case 4:
                return "rami levy Tidhar St 1, Pardes Hanna-Karkur";
            case 5:
                return "rami levy Ha-Mavreg St 1, Tiberias";
            case 6:
                return "rami levy Flieman St 4, Haifa";
            case 7:
                return "rami levy HaZerem St 2, Kadima Zoran";
            case 8:
                return "rami levy HaHaroshet St 12, Raanana";
            default:
                return "rami levy HaHaroshet St 12, Raanana";
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
                return "Happy Market, Efrayim Kishon St 9, Beer Yaakov";
            case 4:
                return "Vegetables store, HaMaavak St 43, Givatayim";
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