package com.api.water_sytem_management_java.controllers;

public record CustomerOutput(String name, String contact, String address, Boolean active, String valve, int mesesEmDivida) {
}
