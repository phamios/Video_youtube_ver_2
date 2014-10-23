package vn.nad.aoeclips.common.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ducna6417
 */
public class Category implements Parcelable {
	private String id;
	private String cateName;
	private String cateIcon;
	private String countItem;

	public Category(String id, String cateName, String cateIcon, String countItem) {
		this.id = id;
		this.cateName = cateName;
		this.cateIcon = cateIcon;
		this.countItem = countItem;
	}
	

	/**
	 * Constructor to use when re-constructing object from a parcel
	 * 
	 * @param in
	 *            a parcel from which to read this object
	 **/
	public Category(Parcel in) {
		readFromParcel(in);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// We just need to write each field into the
		// parcel. When we read from parcel, they
		// will come back in the same order
		dest.writeString(id);
		dest.writeString(cateName);
		dest.writeString(cateIcon);
	}

	/**
	 * Called from the constructor to create this object from a parcel.
	 * 
	 * @param in
	 *            parcel from which to re-create object
	 */
	private void readFromParcel(Parcel in) {
		// We just need to read back each
		// field in the order that it was
		// written to the parcel
		id = in.readString();
		cateName = in.readString();
		cateIcon = in.readString();
	}

	/**
	 * 
	 * This field is needed for Android to be able to create new objects,
	 * individually or as arrays.
	 * 
	 * This also means that you can use use the default constructor to create
	 * the object and use another method to hyrdate it as necessary.
	 * 
	 * I just find it easier to use the constructor. It makes sense for the way
	 * my brain thinks ;-)
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Category createFromParcel(Parcel in) {
			return new Category(in);
		}

		public Category[] newArray(int size) {
			return new Category[size];
		}
	};

	
	/**
	 * @return the id
	 */
	public String getCountItem() {
		return countItem;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setCountItem(String count) {
		this.countItem = count;
	}
	

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the cateName
	 */
	public String getCateName() {
		return cateName;
	}

	/**
	 * @param cateName
	 *            the cateName to set
	 */
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	/**
	 * @return the cateIcon
	 */
	public String getCateIcon() {
		return cateIcon;
	}

	/**
	 * @param cateIcon
	 *            the cateIcon to set
	 */
	public void setCateIcon(String cateIcon) {
		this.cateIcon = cateIcon;
	}
}
