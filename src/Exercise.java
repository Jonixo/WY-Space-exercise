import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

class Exercise {
    public static void main(String[] args) {

        ArrayList<Integer> halfHourPeriods = new ArrayList<Integer>();

        for(int i=0;i<48;i++)
            halfHourPeriods.add(i,0);

        try {
            File myObj = new File("2458843pass-schedule.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();

                String[] splitted= data.split(",");

                int startIndex;
                String[] splitStartHour = splitted[2].split(":");

                startIndex=Integer.parseInt(splitStartHour[0])*2;
                if(splitStartHour[1].equals("30"))
                    startIndex++;

                int endIndex;
                String[] splitendHour = splitted[3].split(":");

                endIndex=Integer.parseInt(splitendHour[0])*2;
                if(splitendHour[1].equals("30"))
                    endIndex++;

                for(int i=startIndex;startIndex<=endIndex;startIndex++)
                    halfHourPeriods.add(startIndex,Integer.parseInt(splitted[1]));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println(Collections.max(halfHourPeriods));
    }
}