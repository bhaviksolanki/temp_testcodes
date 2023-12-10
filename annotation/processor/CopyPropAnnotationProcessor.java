package com.ms.datawise.distn.annotation.processor;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import java.lang.reflect. Field;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.ms.datawise.distn.annotation. CopyProp;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanWrapperImpl;
/**
* Contains methods for Copying properties between objects
* based on Annotations.
*/

public final class CopyPropAnnotationProcessor {

	private CopyPropAnnotation Processor() {
	}
	/**
	* Generic method for copying properties from a source object of Type 's' to
	* a target object of Type 'T'.
	*/
	public static <S, T> void copyFromSrcToTarget (S s, T t) {
	
		if (s != null && t != null) {
			/** BeanWrapperImpl is likely used to access & manipulate properties of Objects */
			
			final BeanWrapperImpl src = new BeanWrapperImpl(s);
			final BeanWrapperImpl target = new BeanWrapperImpl(t);
			Field[] fields = s.getClass().getDeclaredFields();
			List<Field> list = asList (fields);
			list.stream().forEach (f -> copryProperty (f, src, target));
		}
	}
	
	/**
	* Checks if the field has the 'CopyProp' Annotation & retrives its target property name & type. * The method then gets value of source property & if its not null and not equal to '0'/'0.0' * then calls setDefaultTargetProptVal.
	*/
	private static void copryProperty (Field f, BeanWrapperImpl src, BeanWrapperImpl target) { 
		CopyProp copyProp = f.getAnnotation (CopyProp.class);
		if (copyProp != null) {
			String target PropName = copyProp.targetPropName();
			String srcPropName = f.getName();

			Object value = src.getPropertyValue (srcPropName);
			if (value != null && (StringUtils.isNotBlank ((value.toString())) && (!"0".equals (value.toString())) && (!"0.0".equals (value.toString())))) {
				setDefaultTargeProptVal (target, targetPropName, value, copyProp.targetPropType());
			}
		}
	}
	
	
	/* Sets the property value in the target object if the property exists in target Object. */
	private static void setDefaultTarge ProptVal (BeanWrapperImpl target, String targetPropName, Object value, String target PropType) {
		
		List<String> target PropList = Stream. of (target.getPropertyDescriptors ()).collect (Collectors.toList()).s .map (pd -> pd.getName()).collect (Collectors.toList());
		if (CollectionUtils.isNotEmpty(targetPropList) && targetPropList.contains (targetPropName)) {
			if (isBlank (target PropType)) {
				target.setPropertyValue (target PropName, value);
			} else {
				setTargetVal (target, targetPropName, value, target PropType);
			}
		}
	}



	/* Convert the source property value to specified type and then set the value in target object. */
	private static void setTargetVal (BeanWrapperImpl target, String target PropName, Object value, String targetPropType) {
		if ("STRING".equalsIgnoreCase (targetPropType)) {

			target.setPropertyValue (targetPropName, value.toString());
		
		}
		if ("LONG".equalsIgnoreCase (targetPropType)) {
		
			target.setPropertyValue (targetPropName, Long.valueOf (value.toString()));
		}
		if ("DOUBLE".equalsIgnoreCase (targetPropType)) {

			target.setPropertyValue (targetPropName, Double.valueOf (value.toString()));
		}
		if ("UTIL-DATE".equalsIgnoreCase (targetPropType)) {
			Date dt = null;
		}
	
		if ("java.sql.Timestamp".equalsIgnoreCase (value.getClass().getName())) { 
		Timestamp timestamp = (Timestamp) value;
		long milliseconds timestamp.getTime() + (timestamp.getNanos () / 1000000); dt = new Date (milliseconds);
		
		if ("java.sql.Date".equalsIgnoreCase (value.getClass().getName())) {
			java.sql.Date d = (java.sql.Date) value;
			dt = new Date (d.getTime());
			target.setPropertyValue (targetPropName, dt);
		}
	}
}
		
		