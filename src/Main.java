import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Main {

    //Encapsulates reading data from txt file.
    public static int[] readData (String filename, int[] halfHourPeriods) {
        try {
            //Reading file (got the name from function parameter), didn't get the filename from user since It didn't mentioned in task
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            //Loop to read every line one by one and process it
            while (myReader.hasNextLine()) {
                //In loop first action is to read next line
                String data = myReader.nextLine();
                //Split read line with ","
                String[] splitted = data.split(",");
                //Getting starting hour and converting it to index for a loop
                int startIndex;
                String[] splitStartHour = splitted[2].split(":");
                //I'm multiplying hour with two and adding 1 if the M is 30 to get the correlating index.
                startIndex = Integer.parseInt(splitStartHour[0]) * 2;
                if (splitStartHour[1].equals("30"))
                    startIndex++;

                //Same stuff but for end index
                int endIndex;
                String[] splitendHour = splitted[3].split(":");

                endIndex = Integer.parseInt(splitendHour[0]) * 2;
                if (splitendHour[1].equals("30"))
                    endIndex++;
                /*After I figured out the start and end index, I created a loop to fill affected indexes.
                 * Ex. If the starting hour is 14:00 and the ending hour is 16:30 and the downlink is 5.
                 * I converted 14:00 to (14*2)=28. index and 16:30 = (16*3)+1=33. index
                 * Now I'm filling indexes 28-33 with 5.
                 * */
                for (int i = startIndex; startIndex <= endIndex; startIndex++)
                    halfHourPeriods[startIndex] = halfHourPeriods[startIndex] + Integer.parseInt(splitted[1]);

            }
            //closing file reader
            myReader.close();
        } catch (
                FileNotFoundException e) { //Error in case file is not found.
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return halfHourPeriods;
    }

    //Finds max value in given int array
    public static int findMax(int[] values)
    {
        int max = values[0];
        for(int i = 1; i < values.length;i++)
        {
            if(values[i] > max)
            {
                max = values[i];
            }
        }
        return max;
    }

    //Asks user for bandwidth
    public static int getBandwidth(){

        Scanner in = new Scanner(System.in);
        System.out.println("Please enter bandwidth of ground station.");
        int bandwidth = in.nextInt();
        return bandwidth;
    }

    //Compares bandwidth to max downlink and prints result.
    public static void compareAndResult(int max, int bandwidth){

        if(max<=bandwidth)
            System.out.println("Maximum amount of downlink is: "+max+" and ground station's bandwidth is: "+bandwidth+"." +
                    " Ground station can handle this downlink.");
        else
            System.out.println("Maximum amount of downlink is: "+max+" and ground station's bandwidth is: "+bandwidth+"." +
                    " Ground station can't handle this downlink.");
    }


    public static void main(String[] args) {

        /*  I decided to divide a day with 30m intervals since each satellite has a downlink rate measured in units
         *   per 30 minutes.
         *   (ex; index 0 = 00:00, index 1 = 00:30, index 2 = 01:00 ... index 47 = 23:30)
         *    Since I only need to know what is the total downlink every interval I didn't stored any information
         *   regarding satellites names. I found out which hour satellite became available and unavailable,
         *   converted it to correlated index of interval, created a for loop to appended affected indexes and appended
         *   affected indexes with the downlink value read from the file. After that find out the max value in the
         *   array and compared it to inputted ground station bandwidth to check if bandwidth is enough.
         * */

        String filename="pass-schedule.txt";
        int max,bandwidth;

        // Creating an array with 30m intervals
        int[] halfHourPeriods;
        halfHourPeriods = new int[48];

        //filling arraylist with 0 incase no satellite passes for an interval and it makes easier to append
        for(int i=0;i<48;i++)
            halfHourPeriods[i]=0;

        //calling method to read data
        halfHourPeriods= Main.readData(filename,halfHourPeriods);

        //calling method to find max value in array
        max= Main.findMax(halfHourPeriods);

        //calling method to get bandwidth
        bandwidth= Main.getBandwidth();

        //calling method to compare results
        Main.compareAndResult(max,bandwidth);

     }
}