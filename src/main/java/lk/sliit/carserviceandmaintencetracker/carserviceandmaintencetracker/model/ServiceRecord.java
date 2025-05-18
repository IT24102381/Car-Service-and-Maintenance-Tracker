package lk.sliit.carserviceandmaintencetracker.carserviceandmaintencetracker.model;

public class ServiceRecord {
    private String date; //yyyy-MM-dd
    private String serviceType;
    private String mechanic;
    private String oilRenewDate;
    private String lastServiceDate;
    private String alignmentCheckDate;
    private String tyreRenewDate;
    private String specialNotice;

    public ServiceRecord() {
    }

    public ServiceRecord(String date, String serviceType, String mechanic, String oilRenewDate, String lastServiceDate, String alignmentCheckDate, String tyreRenewDate, String specialNotice) {
        this.date = date;
        this.serviceType = serviceType;
        this.mechanic = mechanic;
        this.oilRenewDate = oilRenewDate;
        this.lastServiceDate = lastServiceDate;
        this.alignmentCheckDate = alignmentCheckDate;
        this.tyreRenewDate = tyreRenewDate;
        this.specialNotice = specialNotice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getMechanic() {
        return mechanic;
    }

    public void setMechanic(String mechanic) {
        this.mechanic = mechanic;
    }

    public String getOilRenewDate() {
        return oilRenewDate;
    }

    public void setOilRenewDate(String oilRenewDate) {
        this.oilRenewDate = oilRenewDate;
    }

    public String getLastServiceDate() {
        return lastServiceDate;
    }

    public void setLastServiceDate(String lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public String getAlignmentCheckDate() {
        return alignmentCheckDate;
    }

    public void setAlignmentCheckDate(String alignmentCheckDate) {
        this.alignmentCheckDate = alignmentCheckDate;
    }

    public String getTyreRenewDate() {
        return tyreRenewDate;
    }

    public void setTyreRenewDate(String tyreRenewDate) {
        this.tyreRenewDate = tyreRenewDate;
    }

    public String getSpecialNotice() {
        return specialNotice;
    }

    public void setSpecialNotice(String specialNotice) {
        this.specialNotice = specialNotice;
    }
}