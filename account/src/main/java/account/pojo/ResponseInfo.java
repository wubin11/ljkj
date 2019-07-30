package account.pojo;

import java.io.Serializable;

/**
 * ResponseInfo : 薛思诺定的返回数据的格式标准
 * 
 * code:状态标志 成功或失败
 * message:信息
 *
 */
public class ResponseInfo implements Serializable{
	private Integer code = 0;
	private Object message = "";
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
}
