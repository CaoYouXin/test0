package protocol.spi.business;


/**
 * 
 * @author caolisheng
 * @param <T>
 */
public interface Object2Data<T> {

	void trans(IData4Bizz data, T o);
	
}
