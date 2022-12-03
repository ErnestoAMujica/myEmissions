package com.example.myemissions;

public class EmissionCalculator {
    public enum Source {
        //transportation (kgCO2e/mile)
        taxi(0.3407938384, "Taxi"),
        bus(0.1685139914, "Bus"),
        train(0.0564556472, "Train"),

        diesel_large(0.3371084498, "Large Diesel Car"),
        hybrid_large(0.2120627318, "Large Hybrid Car"),
        hybrid_plugin_large(0.1244180754, "Large Hybrid Plugin Car"),
        gas_large(0.455362753, "Large Gas Car"),
        electric_large(0.1076326592, "Large Electric Car"),
        diesel_medium(0.2745694974, "Medium Diesel Car"),
        hybrid_medium(0.175337593, "Medium Hybrid Car"),
        hybrid_plugin_medium(0.1139895522, "Medium Hybrid Plugin Car"),
        gas_medium(0.3094438952, "Medium Gas Car"),
        electric_medium(0.0855686078, "Medium Electric Car"),
        diesel_small(0.2286550272, "Small Diesel Car"),
        hybrid_small(0.169302568, "Small Hybrid Car"),
        hybrid_plugin_small(0.047234129, "Small Hybrid Plugin Car"),
        gas_small(0.169302568, "Small Gas Car"),
        electric_small(0.0734985578, "Small Electric Car"),

        business_longhaul(0.6991938564, "Long-haul Flight - Business Class"),
        economy_longhaul(0.2410952254, "Long-haul Flight - Economy Class"),
        economyplus_longhaul(0.385758798, "Long-haul Flight - Economy Plus Class"),
        first_longhaul(0.964396995, "Long-haul Flight - First Class"),
        business_shorthaul(0.375941824, "Short-haul Flight - Business Class"),
        economy_shorthaul(0.2506225182, "Short-haul Flight - Economy Class"),

        //Utilities
        //kgCO2e/kwh
        energy(0.433, "Energy"),
        //kgCO2e/therm
        natural_gas(5.3, "Natural Gas");

        private final double value;
        final String name;

        Source(double value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    public double transportation(Source source, double miles) {
        return source.value * miles;
    }

    public double energy(double kwh) {
        return Source.energy.value * kwh;
    }

    public double natural_gas(double therms) {
        return Source.natural_gas.value * therms;
    }

}
