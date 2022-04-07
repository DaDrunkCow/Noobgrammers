package com.yorku.noobgrammers;

// purpose of this class is to create alarm object that will be used to store information of alarm
public class Alarm {

    /*
     * uses 3 separate values to determine whether or not the alarm is repeating
     * value = 0, alarm does not repeat
     * value = 1, alarm repeats daily
     * value = 2, alarm repeats weekly
     */
    /*
     * scrapping above design
     * new repeat is boolean
     * primitive boolean array used to contain the values
     * iteration 0 = sunday
     * iteration 1 = monday
     * iteration 2 = tuesday
     * iteration 3 = wednesday
     * iteration 4 = thursday
     * iteration 5 = friday
     * iteration 6 = saturday
     */
    private static final int DAYS_IN_A_WEEK = 7;
    private  boolean[] repeat = new boolean[DAYS_IN_A_WEEK];

    // alarm time will be stored in 24hr "00:00" string format
    private String time;
    // other alarm information
    private String name;
    private String description;
    private boolean active;
    // TODO: learn how vibration and volume settings work for devices
    /*
     * this are omitted for now due to lack of understanding of them
    private Boolean vibrate;
    private Boolean volume;
    */

    // constructor
    public Alarm ()
    {
        this.time = "00:00";
        this.name = "Alarm";
        this.description = "";
        this.active = true;
        for (int i = 0; i < DAYS_IN_A_WEEK; i++)
        {
            this.repeat[0] = false;
        }
    }

    public Alarm (String time, String name, String repeat)
    {
        this.time = time;
        this.name = name;
        this.description = "";
        this.active = true;
        setRepeat(repeat);
    }

    // setter methods
    public void setTime (String time)
    {
        this.time = time;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public void setActive (boolean set)
    {
        this.active = set;
    }

    public void setRepeat (int i, boolean repeat)
    {
        this.repeat[i] = repeat;
    }

    public void setRepeat (String repeatList)
    {
        for (int i = 0; i < DAYS_IN_A_WEEK; i++)
        {
            this.repeat[i] = repeatList.charAt(i) != '0';
        }
    }

    // getter methods
    public int[] getTime ()
    {
        int[] out = new int[2];
        out[0] = Integer.parseInt(this.time.substring(0, 2));
        out[1] = Integer.parseInt(this.time.substring(3));
        return out;
    }

    public String getPeriod ()
    {
        if (this.getTime()[0] < 12)
        {
            return "am";
        }
        else
        {
            return "pm";
        }
    }

    public String getName ()
    {
        return this.name;
    }

    public String getDescription ()
    {
        return this.description;
    }

    public boolean getActive ()
    {
        return this.active;
    }

    public boolean getRepeat (int i)
    {
        return this.repeat[i];
    }

    public boolean[] getAllRepeat ()
    {
        return this.repeat;
    }

    public String getStrRepeat ()
    {
        String out = "";
        for (boolean i: this.repeat)
        {
            if (i)
            {
                out += "1";
            }
            else
            {
                out += "0";
            }
        }
        return out;
    }

    // method to get everything about the alarm
    public String[] getAlarm ()
    {
        String[] out = new String[3];
        out[0] = this.time;
        out[1] = this.name;
        out[2] = getStrRepeat();
        return out;
    }

}
