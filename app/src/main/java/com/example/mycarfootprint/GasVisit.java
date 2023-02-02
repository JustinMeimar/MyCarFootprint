package com.example.mycarfootprint;

public class GasVisit {

    enum FuelType {
        REGULAR,
        DIESEL
    };

    // Constant Emission Values
    public static double gasEmissions = 2.32;
    public static double dieselEmissions = 2.69;

    // Member variables
    private String gasStationName;
    private String date;
    private FuelType fuelType;
    private Integer fuelAmount;
    private Float fuelUnitPrice;

    private Float totalCost;
    private Float totalFootprint;

    public GasVisit() {}

    // Name of Gas Station Get & Set
    public void setGasStationName(String gasStationName) {
        //do some syntactic assertions
        int len = gasStationName.length();
        if (len == 0 || len > 30) {
            throw new IllegalArgumentException("Illegal Argument!");
        }
        this.gasStationName = gasStationName;
    }
    public String getGasStationName() {
        return this.gasStationName;
    }

    // Date of Re-Fuel Get & Set
    public void setDate(String date) {
        //do some syntactic assertions
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Illegal argument!");
        }
        this.date = date;
    }
    public String getDate() {
        return this.date;
    }

    // Fuel Type Get & Set
    public void setFuelType(FuelType fuelType) {
        //do some syntactic assertions
        this.fuelType = fuelType;
    }
    public FuelType getFuelType() {
        return this.fuelType;
    }

    // Fuel Amount Get & Set
    public void setFuelAmount(Integer fuelAmount) {
        //do some syntactic assertions
        if (fuelAmount == null || fuelAmount < 0) {
            throw new IllegalArgumentException("Fuel amount must be positive"); // pass up exception to caller in VisitsFragment
        }
        this.fuelAmount = fuelAmount;
    }
    public Integer getFuelAmount() {
        return this.fuelAmount;
    }

    // Fuel Unit Price Get & Set
    public void setFuelUnitPrice(Float fuelUnitPrice) {
        //do some syntactic assertions
        if (fuelUnitPrice == null || fuelUnitPrice < 0) {
            throw new IllegalArgumentException("Fuel price must be positive"); // pass up exception to caller in VisitsFragment
        }
        this.fuelUnitPrice = fuelUnitPrice;
    }

    public Float getFuelPrice() { return this.fuelUnitPrice; }

    public Float getTotalFootprint() {
        return this.totalFootprint;
    }
    public void setTotalFootprint(Float footprint) {
        totalFootprint = footprint;
    }
    public Float getTotalCost() {
        return this.totalCost;
    }
    public void setTotalCost(Float cost) {
        totalCost = cost;
    }

    @Override
    public String toString() {
        return this.gasStationName;
    }
}