package seedu.duke.views;

import java.util.ArrayList;
import java.util.List;

public class WeeklyScheduleView {
    private static final int columnWidth = 12;
    private static final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    public static void print(String output) {
        System.out.print(output);
    }

    public static void println(String output) {
        System.out.print(output);
    }

    public static void printHorizontalLine() {
        println("--------------------------------------------------------------------------------------------");
    }

    public static void printVerticalLine() {
        System.out.print("|");
    }

    public static void printlnVerticalLine() {
        System.out.println("|");
    }

    public static void printToJustify(int number) {
        print(String.format("%-"+number+ "s", ""));
    }

    //ideally a function that can be called in Student
    public static void printWeeklySchedule(List<List<ArrayList<String>>> weeklySchedule) {//ArrayList<String>[][] weeklySchedule) {//8am-8pm
        //weeklySchedule[][] should be an Array of Array of Tasks
        //Tasks is an ArrayList of Task
        printDayHeader();
        for (int timePeriod = 0; timePeriod < 12; timePeriod++) { //8-9am index 0, 7-8pm index 11
            printRow(weeklySchedule.get(timePeriod), timePeriod, timePeriod == 11);
        }
    }

    public static void printDayHeader() {
        printHorizontalLine();
        printToJustify(columnWidth);//printblank
        for (int i = 0; i < 7; i++) { //7 days
            printVerticalLine();

            String currentDay = days[i];
            print(currentDay);
            printToJustify(columnWidth - currentDay.length());
        }
        printVerticalLine();
    }

    public static void printRow(List<ArrayList<String>> hourSchedule, int timePeriod, boolean last_line) { //header & 7 days
        boolean tasksPrinted = false;

        //save a copy
        //List<String>[] weeklyTask = new List<String>[8];
        List<ArrayList<String>> weeklyTask = new ArrayList<>();
        String header = getTime(timePeriod);
        weeklyTask.set(0, new ArrayList<String>(List.of(header))); //????
        for (int i = 0; i < 7; i++) { //7 days
            ArrayList<String> task = new ArrayList<String>(hourSchedule.get(i));
            weeklyTask.set(i + 1, task);
        }

        printHorizontalLine();
        while (!tasksPrinted) { //line, tasks

            //print row by row
            for (int i = 0; i <= 7; i++) { //timePeriod + 7 days
                printVerticalLine();
                //for (int t = 0; t < weeklyTask[i].size(); t++) { //print limited char
                //weeklyTask[i] is an ArrayList<String> that contains tasks in that time period
                if (weeklyTask.get(i).isEmpty()) {
                    printToJustify(columnWidth);
                } else {
                    String currentTask = weeklyTask.get(i).get(0); //get 1st task
                    if (currentTask.length() < columnWidth) {
                        print(currentTask);
                        printToJustify(columnWidth - currentTask.length());
                        weeklyTask.get(i).remove(0);
                    } else {
                        String[] words = currentTask.split(" ");
                        int columnWidthLeft = columnWidth;

                        int j = 0;
                        while (words[j].length() < columnWidthLeft) {
                            print(words[j]);
                            words[j] = "";
                            j += 1;
                            columnWidthLeft -= words[j].length();
                        }

                        printToJustify(columnWidthLeft);
                        //currentTask should be updated to start from index j
                        String startingWord = words[j];
                        int startingIndex = currentTask.indexOf(startingWord);
                        weeklyTask.get(i).set(0, currentTask.substring(startingIndex)); //update currentTask
                    }
                }
            }
            printlnVerticalLine();

            boolean thisTaskPrinted = true;
            for (int i = 0; i <= 7; i++) { //timePeriod + 7 days
                if (!weeklyTask.get(i).isEmpty()) {
                    thisTaskPrinted = false; //not finished
                    break;
                }
            }
            tasksPrinted = thisTaskPrinted;
        }
        if (last_line) {
            printHorizontalLine();
        }

    }

    public static String getTime(int timePeriod) {
        switch (timePeriod) {
            case 0:
            case 1:
            case 2:
                return (timePeriod + 8) + "-" + (timePeriod + 9) + "am";
            case 3:
                return (timePeriod + 8) + "am-" + (timePeriod + 9) + "pm";
            case 4:
                return (timePeriod + 8) + "-" + (timePeriod - 3) + "pm";
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                return (timePeriod - 4) + "-" + (timePeriod - 3) + "pm";
            default:
                //do nothing
        }
        return "";
    }
}
