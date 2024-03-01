/************************************************************************
 *                                                                      *
 *      Class Name: BookingRecord                                       *
 *      Purpose: This class is used in order to have getters and setters*
 * and also a constructor. The constructor sets the values for all the  *
 * variables that are entered by the user and passed to it.             *
 ************************************************************************/

package edu.niu.android.hotel;
public class BookingRecord {

    private String name;
    private String email;
    private String number;

    private String fd;
    private String td;
    private double price;
    private String roomType;
    public BookingRecord(String newName, String newnum, String newemail, String newfd, String newtd, double newPrice, String newroomType)
    {
        setName(newName);
        setNumber(newnum);
        setEmail(newemail);
        setfd(newfd);
        settd(newtd);
        setPrice(newPrice);
        setRoomType(newroomType);
    }

    public void setName(String newName)
    {
        name = newName;
    }
    public void setEmail(String newemail)
    {
        email = newemail;
    }
    public void setNumber(String newnum)
    {
        number = newnum;
    }
    public void setfd(String newfd)
    {
        fd = newfd;
    }
    public void settd(String newtd)
    {
        td = newtd;
    }
    public void setPrice(double newPrice)
    {
        price = newPrice;
    }
    public void setRoomType(String newroomType)
    {
        roomType = newroomType;
    }


    public String getName()
    {
        return name;
    }
    public String getEmail()
    {
        return email;
    }
    public String getNumber()
    {
        return number;
    }
    public String getFd()
    {
        return fd;
    }
    public String getTd()
    {
        return td;
    }
    public double getPrice()
    {
        return price;
    }
    public String getRoomType()
    {
        return roomType;
    }


    }
