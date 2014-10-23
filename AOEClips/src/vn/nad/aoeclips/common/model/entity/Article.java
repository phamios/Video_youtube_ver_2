package vn.nad.aoeclips.common.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author ducna6417
 */
public class Article implements Parcelable {
	private String id;
	private String cateid;
	private String image;
	private String name;
	private String link;
	private String duration;

	/**
	 * @param id
	 * @param cateid
	 * @param image
	 * @param name
	 * @param content
	 * @param link
	 * @param duration
	 */
	public Article(String id, String cateid, String image, String name,
			String link, String duration) {
		this.id = id;
		this.cateid = cateid;
		this.image = image;
		this.name = name;
		this.link = link;
		this.duration = duration;
	}

	/**
	 * Standard basic constructor for non-parcel object creation
	 */
	public Article() {
	}

	/**
	 * Constructor to use when re-constructing object from a parcel
	 * 
	 * @param in
	 *            a parcel from which to read this object
	 **/
	public Article(Parcel in) {
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
		dest.writeString(cateid);
		dest.writeString(image);
		dest.writeString(name);
		dest.writeString(link);
		dest.writeString(duration);
	};

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
		cateid = in.readString();
		image = in.readString();
		name = in.readString();
		link = in.readString();
		duration = in.readString();
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
		public Article createFromParcel(Parcel in) {
			return new Article(in);
		}

		public Article[] newArray(int size) {
			return new Article[size];
		}
	};

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
	 * @return the cateid
	 */
	public String getCateid() {
		return cateid;
	}

	/**
	 * @param cateid
	 *            the cateid to set
	 */
	public void setCateid(String cateid) {
		this.cateid = cateid;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

}
