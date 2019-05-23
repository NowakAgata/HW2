package com.example.recyclerview;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    private String id ;
    private String name ;
    private String date;
    private String description;
    private String pic;

    public Person( String id, String name, String date, String description, String pic) {
        this.id = id;
        this.pic = pic;
        this.name = name;
        this.date = date;
        this.description = description;
    }
    public Person( String id,String name, String date, String description) {
        this.id =id;
        this.pic = null;
        this.name = name;
        this.date = date;
        this.description = description;
    }

    protected Person(Parcel in) {
        id = in.readString();
        name = in.readString();
        date = in.readString();
        description = in.readString();
        pic = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    public String getPic() {
        return pic;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }


    public String getId() {return  this.id;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(description);
        dest.writeString(pic);
    }
}
