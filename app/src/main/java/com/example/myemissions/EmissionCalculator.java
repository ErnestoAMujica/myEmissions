package com.example.myemissions;

public class EmissionCalculator {
    public enum Source {
        //transportation (kgCO2e/mile)
        taxi(0.3407938384),
        bus(0.1685139914),
        train(0.0564556472),

        diesel_large(0.3371084498),
        hybrid_large(0.2120627318),
        hybrid_plugin_large(0.1244180754),
        gas_large(0.455362753),
        electric_large(0.1076326592),
        diesel_medium(0.2745694974),
        hybrid_medium(0.175337593),
        hybrid_plugin_medium(0.1139895522),
        gas_medium(0.3094438952),
        electric_medium(0.0855686078),
        diesel_small(0.2286550272),
        hybrid_small(0.169302568),
        hybrid_plugin_small(0.047234129),
        gas_small(0.169302568),
        electric_small(0.0734985578),

        business_longhaul(0.6991938564),
        economy_longhaul(0.2410952254),
        economyplus_longhaul(0.385758798),
        first_longhaul(0.964396995),
        business_shorthaul(0.375941824),
        economy_shorthaul(0.2506225182),

        //Utilities
        //kgCO2e/kwh
        energy(0.433),
        //kgCO2e/therm
        natural_gas(5.3);

        private final double value;

        Source(double value) {
            this.value = value;
        }
    }

    public enum SourceName {
        //transportation (kgCO2e/mile)
        taxi("Taxi"),
        bus("Bus"),
        train("Train"),

//        diesel_large(),
//        hybrid_large(),
//        hybrid_plugin_large(),
//        gas_large(),
//        electric_large(),
        diesel_medium("Diesel Car"),
        hybrid_medium("Hybrid Car"),
//        hybrid_plugin_medium(),
        gas_medium("Car"),
        electric_medium("Electric Car"),
//        diesel_small(),
//        hybrid_small(),
//        hybrid_plugin_small(),
//        gas_small(),
//        electric_small(),

//        business_longhaul(),
//        economy_longhaul(),
//        economyplus_longhaul(),
//        first_longhaul(),
//        business_shorthaul(),
//        economy_shorthaul(),

        //Utilities
        //kgCO2e/kwh
        energy("Energy"),
        //kgCO2e/therm
        natural_gas("Natural Gas");

        public final String emissionName;

        SourceName(String passedInName) {
            this.emissionName = passedInName;
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
