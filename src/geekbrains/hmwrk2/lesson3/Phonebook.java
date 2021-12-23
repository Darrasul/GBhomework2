package geekbrains.hmwrk2.lesson3;

public class Phonebook implements Comparable {

    private String surname;
    private String phoneNumber;

    public Phonebook(String surname, String phoneNumber){
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSurname() {
        return surname;
    }


    @Override
    public int compareTo(Object o) {
        Phonebook otherNumber = (Phonebook)o;
        if (this.phoneNumber != otherNumber.phoneNumber) return 1;
        else return 0;
    }
}
