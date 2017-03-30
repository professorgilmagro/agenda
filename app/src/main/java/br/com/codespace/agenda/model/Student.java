package br.com.codespace.agenda.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.PhoneNumberUtils;

import java.util.Date;
import java.util.Locale;

/**
 * Created by gilmar on 22/03/17.
 */

public class Student implements Parcelable {
    final String MALE = "male";
    final String FEMALE = "female";

    private Long id;
    private String firstName;
    private String lastName;
    private String zipcode;
    private String street;
    private String neighborhood;
    private Integer streetNumber;
    private String complement;
    private String city;
    private String state;
    private Date birthDate;
    private String email;
    private String website;
    private String phoneNumber;
    private Double score;
    private String gender;
    private String photoPath;

    public enum Gender {
        MALE, FEMALE
    }

    public Student() {

    }

    @Override
    public String toString() {
        return String.format("%s - %s", this.getId(), this.getFullName());
    }

    public Boolean exists()
    {
        return this.getId() != null && this.getId() > 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPhoneNumberFormatted() {
        return PhoneNumberUtils.formatNumber(phoneNumber, Locale.getDefault().getCountry());
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null) {
            phoneNumber = PhoneNumberUtils.normalizeNumber(phoneNumber);
        }

        this.phoneNumber = phoneNumber;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    /**
     * Retorna o endereço completo do aluno
     *
     * @return String
     */
    public String getAddress() {
        return String.format(
                "%s, %s - %s, %s, %s",
                this.getStreet(),
                this.getStreetNumber(),
                this.getNeighborhood(),
                this.getCity(),
                this.getState()
        );
    }

    @Override
   public int describeContents() {
       return 0;
   }

   /**
    * Storing the Student data to Parcel object
    **/
   @Override
   public void writeToParcel(Parcel dest, int flags) {
       dest.writeLong(id);
       dest.writeString(firstName);
       dest.writeString(lastName);
       dest.writeString(zipcode);
       dest.writeString(street);
       dest.writeString(neighborhood);
       dest.writeInt(streetNumber);
       dest.writeString(complement);
       dest.writeString(city);
       dest.writeString(state);
       dest.writeLong(birthDate != null ? birthDate.getTime() : -1);
       dest.writeString(email);
       dest.writeString(website);
       dest.writeString(phoneNumber);
       dest.writeDouble(score);
       dest.writeString(gender);
       dest.writeString(photoPath);
   }

   /**
    * Retrieving Student data from Parcel object
    * This constructor is invoked by the method createFromParcel(Parcel source) of
    * the object CREATOR
    **/
   private Student(Parcel in) {
       this.id = in.readLong();
       this.firstName = in.readString();
       this.lastName = in.readString();
       this.zipcode = in.readString();
       this.street = in.readString();
       this.neighborhood = in.readString();
       this.streetNumber = in.readInt();
       this.complement = in.readString();
       this.city = in.readString();
       this.state = in.readString();
       Long tmpDate = in.readLong();
       this.birthDate = tmpDate == 1 ? null : new Date(tmpDate);
       this.email = in.readString();
       this.website = in.readString();
       this.phoneNumber = in.readString();
       this.score = in.readDouble();
       this.gender = in.readString();
       this.photoPath = in.readString();
   }

   public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
       @Override
       public Student createFromParcel(Parcel source) {
           return new Student(source);
       }

       @Override
       public Student[] newArray(int size) {
           return new Student[size];
       }
   };
}
