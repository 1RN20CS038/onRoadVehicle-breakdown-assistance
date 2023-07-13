package com.example.onroadservice;



public class Registered {
        String name;
        String email;
        String password;
        String role;
        String phone;

    public Registered() {
        // Default constructor required for Firebase
    }

        public Registered(String name, String email, String password, String role, String phone) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.role = role;
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getRole() {
            return role;
        }

        public String getPhone() {
            return phone;
        }

}
