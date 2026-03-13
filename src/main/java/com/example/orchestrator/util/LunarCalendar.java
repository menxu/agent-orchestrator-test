package com.example.orchestrator.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class LunarCalendar {

    private static final long[] LUNAR_INFO = {
        0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
        0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
        0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
        0x06566, 0x0d4a0, 0x0ea50, 0x16a95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950,
        0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557,
        0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5b0, 0x14573, 0x052b0, 0x0a9a8, 0x0e950, 0x06aa0,
        0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0,
        0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b6a0, 0x195a6,
        0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570,
        0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x05ac0, 0x0ab60, 0x096d5, 0x092e0,
        0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5,
        0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
        0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
        0x05aa0, 0x076a3, 0x096d0, 0x04afb, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
        0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0,
        0x14b63, 0x09370, 0x049f8, 0x04970, 0x064b0, 0x168a6, 0x0ea50, 0x06b20, 0x1a6c4, 0x0aae0,
        0x092e0, 0x0d2e3, 0x0c960, 0x0d557, 0x0d4a0, 0x0da50, 0x05d55, 0x056a0, 0x0a6d0, 0x055d4,
        0x052d0, 0x0a9b8, 0x0a950, 0x0b4a0, 0x0b6a6, 0x0ad50, 0x055a0, 0x0aba4, 0x0a5b0, 0x052b0,
        0x0b273, 0x06930, 0x07337, 0x06aa0, 0x0ad50, 0x14b55, 0x04b60, 0x0a570, 0x054e4, 0x0d160,
        0x0e968, 0x0d520, 0x0daa0, 0x16aa6, 0x056d0, 0x04ae0, 0x0a9d4, 0x0a2d0, 0x0d150, 0x0f252
    };

    private static final String[] HEAVENLY_STEMS = {"Jia", "Yi", "Bing", "Ding", "Wu", "Ji", "Geng", "Xin", "Ren", "Gui"};
    private static final String[] EARTHLY_BRANCHES = {"Zi", "Chou", "Yin", "Mao", "Chen", "Si", "Wu", "Wei", "Shen", "You", "Xu", "Hai"};
    private static final String[] ZODIAC = {"Rat", "Ox", "Tiger", "Rabbit", "Dragon", "Snake", "Horse", "Goat", "Monkey", "Rooster", "Dog", "Pig"};
    private static final String[] WEEKDAYS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private static final String[] LUNAR_MONTH_NAMES = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th", "11th", "12th"};
    private static final String[] LUNAR_DAY_NAMES = {
        "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th", "10th",
        "11th", "12th", "13th", "14th", "15th", "16th", "17th", "18th", "19th", "20th",
        "21st", "22nd", "23rd", "24th", "25th", "26th", "27th", "28th", "29th", "30th"
    };

    private static final Map<Integer, String[]> SOLAR_TERMS = new HashMap<>();

    static {
        String[][] terms = {
            {"Xiao Han", "Da Han"}, {"Li Chun", "Yu Shui"}, {"Jing Zhe", "Chun Fen"},
            {"Qing Ming", "Gu Yu"}, {"Li Xia", "Xiao Man"}, {"Mang Zhong", "Xia Zhi"},
            {"Xiao Shu", "Da Shu"}, {"Li Qiu", "Chu Shu"}, {"Bai Lu", "Qiu Fen"},
            {"Han Lu", "Shuang Jiang"}, {"Li Dong", "Xiao Xue"}, {"Da Xue", "Dong Zhi"}
        };
        for (int i = 0; i < 12; i++) {
            SOLAR_TERMS.put(i + 1, terms[i]);
        }
    }

    public static class LunarDate {
        public int year;
        public int month;
        public int day;
        public boolean isLeapMonth;
        public String yearGanZhi;
        public String zodiac;

        public LunarDate(int year, int month, int day, boolean isLeapMonth) {
            this.year = year;
            this.month = month;
            this.day = day;
            this.isLeapMonth = isLeapMonth;
            this.yearGanZhi = getYearGanZhi(year);
            this.zodiac = getZodiac(year);
        }

        @Override
        public String toString() {
            String monthStr = (isLeapMonth ? "Leap " : "") + LUNAR_MONTH_NAMES[month - 1] + " month";
            String dayStr = LUNAR_DAY_NAMES[day - 1];
            return yearGanZhi + " Year [" + zodiac + "] " + monthStr + " " + dayStr;
        }
    }

    public static LunarDate solarToLunar(LocalDate date) {
        int offset = (int) (date.toEpochDay() - LocalDate.of(1900, 1, 31).toEpochDay());
        int targetYear = 1900;

        for (int i = 1900; i <= 2100; i++) {
            int daysInYear = getDaysInYear(i);
            if (offset < daysInYear) {
                targetYear = i;
                break;
            }
            offset -= daysInYear;
        }

        int leapMonth = getLeapMonth(targetYear);
        int targetMonth = 1;
        boolean isLeap = false;

        for (int i = 1; i <= 12; i++) {
            if (leapMonth > 0 && i == leapMonth + 1) {
                int daysInMonth = getLeapMonthDays(targetYear);
                if (offset < daysInMonth) {
                    targetMonth = i - 1;
                    isLeap = true;
                    break;
                }
                offset -= daysInMonth;
            }
            int daysInMonth = getMonthDays(targetYear, i);
            if (offset < daysInMonth) {
                targetMonth = i;
                break;
            }
            offset -= daysInMonth;
        }

        int targetDay = offset + 1;
        return new LunarDate(targetYear, targetMonth, targetDay, isLeap);
    }

    private static int getDaysInYear(int year) {
        int days = 0;
        for (int i = 1; i <= 12; i++) {
            days += getMonthDays(year, i);
        }
        if (getLeapMonth(year) > 0) {
            days += getLeapMonthDays(year);
        }
        return days;
    }

    private static int getMonthDays(int year, int month) {
        long info = LUNAR_INFO[year - 1900];
        return (((info >> (16 - month)) & 0x1) == 1) ? 30 : 29;
    }

    private static int getLeapMonth(int year) {
        return (int) (LUNAR_INFO[year - 1900] & 0xf);
    }

    private static int getLeapMonthDays(int year) {
        return (((LUNAR_INFO[year - 1900] >> 20) & 0x1) == 1) ? 30 : 29;
    }

    private static String getYearGanZhi(int year) {
        int stemIndex = (year - 4) % 10;
        int branchIndex = (year - 4) % 12;
        if (stemIndex < 0) stemIndex += 10;
        if (branchIndex < 0) branchIndex += 12;
        return HEAVENLY_STEMS[stemIndex] + EARTHLY_BRANCHES[branchIndex];
    }

    private static String getZodiac(int year) {
        return ZODIAC[(year - 4) % 12];
    }

    public static String getWeekday(LocalDate date) {
        return WEEKDAYS[date.getDayOfWeek().getValue() % 7];
    }

    public static String getSolarTerm(LocalDate date) {
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        int[] firstTermDay = {6, 4, 6, 5, 6, 6, 7, 8, 8, 9, 8, 7};
        int[] secondTermDay = {21, 19, 21, 20, 21, 22, 23, 23, 23, 24, 22, 22};

        String[] terms = SOLAR_TERMS.get(month);
        if (terms == null) {
            return "";
        }
        if (day == firstTermDay[month - 1] || day == firstTermDay[month - 1] + 1) {
            return terms[0];
        } else if (day == secondTermDay[month - 1] || day == secondTermDay[month - 1] + 1) {
            return terms[1];
        }
        return "";
    }
}
