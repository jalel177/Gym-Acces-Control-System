package com.example.demo.DTO;


public class UserUpdatedto {


    public static class UserUpdateDTO {
        private String firstName; // Changed to match getter/setter
        private String lastName;
        private String email;
        private String username;
        private String password;
        private String address;

        public String getUsername() {
            return username;
        }

        public UserUpdateDTO setUsername(String username) {
            this.username = username;
            return this;
        }

        public String getFirstName() {
            return firstName;
        }

        public UserUpdateDTO setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public String getLastName() {
            return lastName;
        }

        public UserUpdateDTO setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public UserUpdateDTO setEmail(String email) {
            this.email = email;
            return this;
        }

        public String getPassword() {
            return password;
        }

        public UserUpdateDTO setPassword(String password) {
            this.password = password;
            return this;
        }

        public String getAddress() {
            return address;
        }

        public UserUpdateDTO setAddress(String address) {
            this.address = address;
            return this;
        }
    }
}


