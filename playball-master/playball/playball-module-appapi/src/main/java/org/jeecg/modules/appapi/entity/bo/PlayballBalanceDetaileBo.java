package org.jeecg.modules.appapi.entity.bo;

import java.io.Serializable;
import java.util.List;
import org.jeecg.modules.appapi.entity.PlayballBalanceDetaile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PlayballBalanceDetaileBo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String days;
	private List<PlayballBalanceDetaile> balances;
}
