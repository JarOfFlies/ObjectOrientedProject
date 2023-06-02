package com.library.Classes.People;

/**
 * PeopleBase
 */
public abstract class PeopleBase {

    public int Id;
    
    public String Forename;

    public String Surname;

    public String Email;

    public int PhoneNumber;

    abstract void CreateAccount();
}
