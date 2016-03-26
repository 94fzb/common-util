package com.fzb.common;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CitySimpileFind {

    public static Set<String> CITYSET = new HashSet<String>();

    static {
        try {
            byte[] cityBytes = getBytesByInputStream(CitySimpileFind.class.getResourceAsStream("/city.db"));
            String cityArr[] = new String(cityBytes).replace("\n", "").split("  ");
            for (String city : cityArr) {

                if (!"".equals(city)) {
                    CITYSET.add(city.replace("\r", ""));
                }

            }
            System.out.println(new String(cityBytes).replace("\n", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static byte[] getBytesByInputStream(InputStream inw) throws IOException {
        int len = 0;
        byte[] b = new byte[1024];
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        while ((len = inw.read(b)) != -1) {
            out.write(b, 0, len);
        }
        return out.toByteArray();
    }

    public static void main(String[] args) {


        Map<String, Object> cityMap = new TreeMap<String, Object>();
        //CITYSET.clear();
        //CITYSET.add("安次");
        //CITYSET.add("阿勒泰地");
        for (String city : CITYSET) {

            HanyuPinyinOutputFormat out = new HanyuPinyinOutputFormat();
            out.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

            String firstPy = "";
            String allPy = "";
            for (int i = 0; i < city.length(); i++) {
                String p = null;
                try {
                    p = PinyinHelper.toHanyuPinyinString(city.charAt(i) + "", out, null);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
                allPy += p;
                firstPy += p.substring(0, 1);
            }
            if (city.length() < 4) {
                putCity(firstPy, city, cityMap);
                //break;
            }

			/*
            for(int i=0;i<city.length();i++){
				String p=PinyinHelper.toHanyuPinyinString(city.charAt(i)+"", out,null);
				otPy+=p;
			}
			
			if(!cityMap.containsKey(firstPy)){
				Set<String> citys=new HashSet<String>();
				citys.add(city);
				cityMap.put(firstPy, citys);
			}
			else{
				cityMap.get(firstPy).add(city);
			}
			
			if(!cityMap.containsKey(otPy)){
				Set<String> citys=new HashSet<String>();
				citys.add(city);
				cityMap.put(otPy, citys);
			}
			else{
				cityMap.get(otPy).add(city);
			}*/
        }
        System.out.println(cityMap);
        Scanner sc = new Scanner(System.in);
        String qcity = sc.nextLine();
        Set<String> data = new HashSet<String>();
        Map<String, Object> tempMap = cityMap;
        for (int i = 0; i < qcity.length(); i++) {
            tempMap = (Map<String, Object>) tempMap.get(qcity.charAt(i) + "");
        }
        data.addAll((Set) tempMap.get("data"));
        System.out.println(data);
    }

    public static Map<String, Object> putCity(String py, String city, Map<String, Object> cityMap) {
        int length = py.length();
        for (int i = 0; i < length; i++) {
            ((Set) isExists(py, i, cityMap)).add(city);
        }
        //put(py.split(""), cityMap, city);
        return cityMap;
    }

    public static Set<String> findCity(String py) {
        return null;
    }

    public static Set<String> isExists(String py, int cs, Map<String, Object> cityMap) {
        int length = py.length();
        Set<String> set = new HashSet<String>();
        Map<String, Object> tmap = cityMap;
        for (int i = cs; i < length; i++) {
            String ch = py.charAt(i) + "";
            if (i < cs) {
                if (cityMap.get(ch) != null) {
                    cityMap = (Map<String, Object>) cityMap.get(ch);
                    System.out.println(cityMap);
                    isExists(py, i, cityMap);
                }
            }
            String pch = null;
            if (i > 0) {
                pch = py.charAt(i - 1) + "";
            }
            if (pch != null) {
                Map<String, Object> map = new TreeMap<String, Object>();
                map.put("data", set);
                cityMap = (Map<String, Object>) cityMap.get(pch);
                if (cityMap == null) {
                    cityMap = new HashMap<String, Object>();
                }
                cityMap.put(pch, map);
            } else {
                if (cityMap.get(ch) != null) {
                    cityMap = (Map<String, Object>) cityMap.get(ch);
                    set = (Set<String>) cityMap.get("data");
                } else {
                    Map<String, Object> map = new TreeMap<String, Object>();
                    map.put("data", set);
                    cityMap.put(ch, map);
                }
            }
        }
        return set;
    }

    public static void put(String[] strarr, Map<String, Object> cityMap, String city) {
        Map<String, Object> temp = new HashMap<String, Object>();
        for (int i = 0; i < strarr.length; i++) {
            if (!strarr[i].equals("")) {
                if (cityMap != null && cityMap.get(strarr[i]) != null) {
                    temp = (Map<String, Object>) cityMap.get(i + 1);
                } else {
                    Set<String> citySet = null;
                    System.out.println(cityMap.get(strarr[i - 1]) + ">>>>>>>>>");
                    if (cityMap.get(strarr[i - 1]) != null) {
                        citySet = (Set<String>) ((Map<String, Object>) cityMap.get(strarr[i - 1])).get("data");
                        System.out.println(citySet);
                        if (citySet == null) {
                            citySet = new HashSet<String>();
                        }
                        temp = new HashMap<String, Object>();
                    } else {
                        if (temp == null) {
                            temp = new HashMap<String, Object>();
                            citySet = new HashSet<String>();
                        } else {
                            citySet = (Set<String>) temp.get("data");
                            if (citySet == null) {
                                citySet = new HashSet<String>();
                            }
                        }
                    }


                    Map<String, Object> ncityMap = new TreeMap<String, Object>();

                    citySet.add(city);
                    ncityMap.put("data", citySet);
                    temp.put(strarr[i], ncityMap);
                    System.out.println(temp);
                }
            }
        }
        cityMap.putAll(temp);
    }
}
