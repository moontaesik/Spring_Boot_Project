
package com.reservation.foodTable.dto;

import javax.annotation.Generated;

@Generated("jsonschema2pojo")
public class KakaOUserProfile {

	private Long id;
	private String connected_at;
	private Properties properties;
	private KakaoAccount kakao_account;

	@Generated("jsonschema2pojo")
	public class Properties {

		private String nickname;

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

	}

	@Generated("jsonschema2pojo")
	public class KakaoAccount {

		private Boolean profile_nickname_needs_agreement;
		private Profile profile;
		private Boolean has_email;
		private Boolean email_needs_agreement;
		private Boolean is_email_valid;
		private Boolean is_email_verified;
		private String email;

		@Generated("jsonschema2pojo")
		public class Profile {

			private String nickname;

			public String getNickname() {
				return nickname;
			}

			public void setNickname(String nickname) {
				this.nickname = nickname;
			}

		}

		public Boolean getProfile_nickname_needs_agreement() {
			return profile_nickname_needs_agreement;
		}

		public void setProfile_nickname_needs_agreement(Boolean profile_nickname_needs_agreement) {
			this.profile_nickname_needs_agreement = profile_nickname_needs_agreement;
		}

		public Boolean getIs_email_valid() {
			return is_email_valid;
		}

		public void setIs_email_valid(Boolean is_email_valid) {
			this.is_email_valid = is_email_valid;
		}

		public Boolean getIs_email_verified() {
			return is_email_verified;
		}

		public void setIs_email_verified(Boolean is_email_verified) {
			this.is_email_verified = is_email_verified;
		}

		public Profile getProfile() {
			return profile;
		}

		public void setProfile(Profile profile) {
			this.profile = profile;
		}



		public Boolean getHas_email() {
			return has_email;
		}

		public void setHas_email(Boolean has_email) {
			this.has_email = has_email;
		}

		public Boolean getEmail_needs_agreement() {
			return email_needs_agreement;
		}

		public void setEmail_needs_agreement(Boolean email_needs_agreement) {
			this.email_needs_agreement = email_needs_agreement;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConnected_at() {
		return connected_at;
	}

	public void setConnected_at(String connected_at) {
		this.connected_at = connected_at;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public KakaoAccount getKakao_account() {
		return kakao_account;
	}

	public void setKakao_account(KakaoAccount kakao_account) {
		this.kakao_account = kakao_account;
	}

	

}
