package com.ehu.entity.primary;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;


/**
 * The persistent class for the t_merchant_user database table.
 * 
 */
@Entity
@Table(name="t_merchant_user")
@NamedQuery(name="MerchantUser.findAll", query="SELECT m FROM MerchantUser m")
public class MerchantUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer guid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="hx_password")
	private String hxPassword;

	@Column(name="hx_username")
	private String hxUsername;

	@Column(name="pass_word")
	private String password;

	private BigInteger phone;

	@Column(name="push_token")
	private String pushToken;

	@Column(name="real_name")
	private String realName;

	@Column(name="secret_key")
	private String secretKey;

	private Integer smiid;

	private Integer termtyp;

	public MerchantUser() {
	}

	public Integer getGuid() {
		return this.guid;
	}

	public void setGuid(Integer guid) {
		this.guid = guid;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getHxPassword() {
		return this.hxPassword;
	}

	public void setHxPassword(String hxPassword) {
		this.hxPassword = hxPassword;
	}

	public String getHxUsername() {
		return this.hxUsername;
	}

	public void setHxUsername(String hxUsername) {
		this.hxUsername = hxUsername;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigInteger getPhone() {
		return this.phone;
	}

	public void setPhone(BigInteger phone) {
		this.phone = phone;
	}

	public String getPushToken() {
		return this.pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSecretKey() {
		return this.secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Integer getSmiid() {
		return this.smiid;
	}

	public void setSmiid(Integer smiid) {
		this.smiid = smiid;
	}

	public Integer getTermtyp() {
		return this.termtyp;
	}

	public void setTermtyp(Integer termtyp) {
		this.termtyp = termtyp;
	}

}