package com.bakerystore.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.bakerystore.domain.DelicacyToCartItem;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity

public class Delicacy {

	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;//name of the delicacy
	private String chef; //the person who prepares the delicacy
	@Column(columnDefinition="text")
	private String recipe;
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date expiryDate;
	private String cookingType;
	private String category;
	private int kcal_100g;
	private String ingredients;
	private int fssai;
	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public int getFssai() {
		return fssai;
	}

	public void setFssai(int fssai) {
		this.fssai = fssai;
	}

	private double shippingWeight;
	private double listPrice;
	private double ourPrice;
	private boolean active=true;
	
	@Column(columnDefinition="text")// to support very long strings
	private String description;
	private int inStockNumber;
	
	@Transient// this wont be stored in database
	private MultipartFile delicacyImage;
	
	@OneToMany(mappedBy="delicacy")
	@JsonIgnore
	private List<DelicacyToCartItem> delicacyToCartItemList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChef() {
		return chef;
	}

	public void setChef(String chef) {
		this.chef = chef;
	}

	public String getRecipe() {
		return recipe;
	}

	public void setRecipe(String recipe) {
		this.recipe = recipe;
	}

	

	

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCookingType() {
		return cookingType;
	}

	public void setCookingType(String cookingType) {
		this.cookingType = cookingType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getKcal_100g() {
		return kcal_100g;
	}

	public void setKcal_100g(int kcal_100g) {
		this.kcal_100g = kcal_100g;
	}

	public double getShippingWeight() {
		return shippingWeight;
	}

	public void setShippingWeight(double shippingWeight) {
		this.shippingWeight = shippingWeight;
	}

	public double getListPrice() {
		return listPrice;
	}

	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}

	public double getOurPrice() {
		return ourPrice;
	}

	public void setOurPrice(double ourPrice) {
		this.ourPrice = ourPrice;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getInStockNumber() {
		return inStockNumber;
	}

	public void setInStockNumber(int inStockNumber) {
		this.inStockNumber = inStockNumber;
	}

	public MultipartFile getDelicacyImage() {
		return delicacyImage;
	}

	public void setDelicacyImage(MultipartFile delicacyImage) {
		this.delicacyImage = delicacyImage;
	}

	public List<DelicacyToCartItem> getDelicacyToCartItemList() {
		return delicacyToCartItemList;
	}

	public void setDelicacyToCartItemList(List<DelicacyToCartItem> delicacyToCartItemList) {
		this.delicacyToCartItemList = delicacyToCartItemList;
	}
	
	
	
}
