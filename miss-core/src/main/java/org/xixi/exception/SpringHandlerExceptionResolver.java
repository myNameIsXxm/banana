package org.xixi.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;

public class SpringHandlerExceptionResolver implements HandlerExceptionResolver, Ordered {

	private static Logger logger = LoggerFactory.getLogger(SpringHandlerExceptionResolver.class);

	private int order = Ordered.HIGHEST_PRECEDENCE;

	private FastJsonConfig fastJsonConfig;

	@Autowired
	public SpringHandlerExceptionResolver(FastJsonConfig fastJsonConfig) {
		this.fastJsonConfig = fastJsonConfig;
	}

	@Override
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView mv = specialExceptionResolve(ex, request);
		if (null == mv) {
			String message = ex.getMessage();
			if (message == null || message == "") {
				message = "ϵͳ�쳣������ϵ����Ա";
			}
			mv = errorResult(message, "/error", request);
		}
		return mv;
	}

	/**
	 * ��������ǿ���
	 * {@link org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver#doResolveException},
	 * 
	 * @param ex
	 *            �쳣��Ϣ
	 * @param request
	 *            ��ǰ�������(�����жϵ�ǰ�����Ƿ�Ϊajax����)
	 * @return ��ͼģ�Ͷ���
	 */
	private ModelAndView specialExceptionResolve(Exception ex, HttpServletRequest request) {
		try {
			if (ex instanceof NoSuchRequestHandlingMethodException || ex instanceof NoHandlerFoundException) {
				return result(HttpExceptionEnum.NOT_FOUND_EXCEPTION, request);
			} else if (ex instanceof HttpRequestMethodNotSupportedException) {
				return result(HttpExceptionEnum.NOT_SUPPORTED_METHOD_EXCEPTION, request);
			} else if (ex instanceof HttpMediaTypeNotSupportedException) {
				return result(HttpExceptionEnum.NOT_SUPPORTED_MEDIA_TYPE_EXCEPTION, request);
			} else if (ex instanceof HttpMediaTypeNotAcceptableException) {
				return result(HttpExceptionEnum.NOT_ACCEPTABLE_MEDIA_TYPE_EXCEPTION, request);
			} else if (ex instanceof MissingPathVariableException) {
				return result(HttpExceptionEnum.NOT_SUPPORTED_METHOD_EXCEPTION, request);
			} else if (ex instanceof MissingServletRequestParameterException) {
				return result(HttpExceptionEnum.MISSING_REQUEST_PARAMETER_EXCEPTION, request);
			} else if (ex instanceof ServletRequestBindingException) {
				return result(HttpExceptionEnum.REQUEST_BINDING_EXCEPTION, request);
			} else if (ex instanceof ConversionNotSupportedException) {
				return result(HttpExceptionEnum.NOT_SUPPORTED_CONVERSION_EXCEPTION, request);
			} else if (ex instanceof TypeMismatchException) {
				return result(HttpExceptionEnum.TYPE_MISMATCH_EXCEPTION, request);
			} else if (ex instanceof HttpMessageNotReadableException) {
				return result(HttpExceptionEnum.MESSAGE_NOT_READABLE_EXCEPTION, request);
			} else if (ex instanceof HttpMessageNotWritableException) {
				return result(HttpExceptionEnum.MESSAGE_NOT_WRITABLE_EXCEPTION, request);
			} else if (ex instanceof MethodArgumentNotValidException) {
				return result(HttpExceptionEnum.NOT_VALID_METHOD_ARGUMENT_EXCEPTION, request);
			} else if (ex instanceof MissingServletRequestPartException) {
				return result(HttpExceptionEnum.MISSING_REQUEST_PART_EXCEPTION, request);
			} else if (ex instanceof BindException) {
				return result(HttpExceptionEnum.BIND_EXCEPTION, request);
			} else if (ex instanceof AsyncRequestTimeoutException) {
				return result(HttpExceptionEnum.ASYNC_REQUEST_TIMEOUT_EXCEPTION, request);
			} else if (ex instanceof AuthorizationException) {
				return result(HttpExceptionEnum.AUTH_EXCEPTION, request);
			} else if (ex instanceof CannotCreateTransactionException) {
				return result(HttpExceptionEnum.CAN_NOT_CREATE_TRANSACTION_EXCEPTION, request);
			}else if(ex instanceof NullPointerException){
				return result(HttpExceptionEnum.NULL_POINTER_EXCEPTION, request);
			}else if(ex instanceof TransactionException){
				return result(HttpExceptionEnum.HIBER_TRAN_EXCEPTION, request);
			}
		} catch (Exception handlerException) {
			logger.warn("Handling of [" + ex.getClass().getName() + "] resulted in Exception", handlerException);
		}
		if (isAjax(request)) {
			return jsonResult(2000, ex.getMessage());
		} else {
			return normalResult(ex.getMessage(), "/error");
		}
	}

	/**
	 * �ж��Ƿ�ajax����
	 *
	 * @param request
	 *            �������
	 * @return true:ajax���� false:��ajax����
	 */
	private boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
	}

	/**
	 * ���ش�����Ϣ
	 *
	 * @param message
	 *            ������Ϣ
	 * @param url
	 *            ����ҳurl
	 * @param request
	 *            �������
	 * @return ģ����ͼ����
	 */
	private ModelAndView errorResult(String message, String url, HttpServletRequest request) {
		logger.warn("������ʧ�ܣ�����url=[{}], ʧ��ԭ�� : {}", request.getRequestURI(), message);
		if (isAjax(request)) {
			return jsonResult(500, message);
		} else {
			return normalResult(message, url);
		}
	}

	/**
	 * �����쳣��Ϣ
	 *
	 * @param httpException
	 *            �쳣��Ϣ
	 * @param request
	 *            �������
	 * @return ģ����ͼ����
	 */
	private ModelAndView result(HttpExceptionEnum httpException, HttpServletRequest request) {
		logger.warn("������ʧ�ܣ�����url=[{}], ʧ��ԭ�� : {}", request.getRequestURI(), httpException.getMessage());
		if (isAjax(request)) {
			return jsonResult(httpException.getCode(), httpException.getMessage());
		} else {
			return normalResult(httpException.getMessage(), httpException.getUrl());
		}
	}

	/**
	 * ���ش���ҳ
	 *
	 * @param message
	 *            ������Ϣ
	 * @param url
	 *            ����ҳurl
	 * @return ģ����ͼ����
	 */
	private ModelAndView normalResult(String message, String url) {
		Map<String, String> model = new HashMap<String, String>();
		model.put("errorMessage", message);
		return new ModelAndView(url, model);
	}

	/**
	 * ���ش�������
	 *
	 * @param message
	 *            ������Ϣ
	 * @return ģ����ͼ����
	 */
	private ModelAndView jsonResult(int code, String message) {
		ModelAndView mv = new ModelAndView();
		FastJsonJsonView view = new FastJsonJsonView();
		view.setFastJsonConfig(fastJsonConfig);
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("errCode", code);
		attributes.put("errMsg", message);
		view.setAttributesMap((JSONObject) JSON.toJSON(attributes));
		mv.setView(view);
		return mv;
	}
}
