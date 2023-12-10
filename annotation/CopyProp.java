
/**
*
@author dhasolan
CopyProp Annotation is used to mark fields in a class It specifies two attributes:
targetPropName: Name of the property in the target object
where the value is to be copied.
targetPropType: Type of target property. If not specified, the CopyPropProcessor code attempts to infer the type from the source property.
*/

@Target ( FIELD })
@Retention (RUNTIME)
public @interface CopyProp {

	String target PropName();
	String target PropType () default "";
}