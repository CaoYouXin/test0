package protocol.spi.business;

/**
 * 
 * @author caolisheng
 * @param <T>
 */
public interface Object2Data<T> {

	IData4Bizz trans(T o);
	
}
