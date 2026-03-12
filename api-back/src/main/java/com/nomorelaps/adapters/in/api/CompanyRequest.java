package com.nomorelaps.adapters.in.api;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class CompanyRequest {
    
    private Long id;

    private String name;

    private String apiKey;

    private String password;

    private String phone;

    private String email;

    private String cif;

    private LocalDateTime registerDay;

    /**
     * Empty constructor
     */
    public CompanyRequest() {
    }

    /**
     * Constructor with the primary key of the Company
     * @param id identifier of the company
     */
    public CompanyRequest(Long id){
        this.id = id;
    }

    /**
     * Constructor with all the attributes of the Company
     * @param id identifier of the company
     * @param name name of the company
     * @param apikey of the company
     * @param password of the company
     * @param phone phone number of the company
     * @param email corporate email of the company
     * @param cif cif of the company
     * @param registerDay date of the company registration
     * @return
     */
    public CompanyRequest(Long id, String name,String apiKey, String password, String phone,
                          String email, String cif,LocalDateTime registerDay) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.cif = cif;
        this.registerDay = registerDay;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiKey(){
        return apiKey;
    }

    public void setApiKey(String apiKey){
        this.apiKey=apiKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public LocalDateTime getRegisterDay() {
        return registerDay;
    }

    public void setRegisterDay(LocalDateTime registerDay) {
        this.registerDay = registerDay;
    }



    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CompanyRequest)) {
            return false;
        }
        CompanyRequest companyRequest = (CompanyRequest) o;
        return Objects.equals(id, companyRequest.id) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    

}
