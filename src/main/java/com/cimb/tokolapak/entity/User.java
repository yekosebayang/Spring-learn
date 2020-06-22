package com.cimb.tokolapak.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		
		private String username;
		
		private String password;
		
		private String profilePicture;
		
		private boolean verifikasi;
		
		public boolean isVerifikasi() {
			return verifikasi;
		}
		public void setVerifikasi(boolean verifikasi) {
			this.verifikasi = verifikasi;
		}
		public String getProfilePicture() {
			return profilePicture;
		}
		public void setProfilePicture(String profilePicture) {
			this.profilePicture = profilePicture;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	
}
