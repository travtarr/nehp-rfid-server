package com.nehp.rfid_system.server.auth.annotation;

import java.lang.annotation.*;

import com.nehp.rfid_system.server.core.Authority;

/**
 * <p>Annotation to provide the following to application:</p>
 * <ul>
 * <li>Concise type-safe reference to {@link com.nehp.rfid_system.server.core.Authority}</li>
 * <li>Binds to parameter to assist injection of User</li>
 * </ul>
 * <p>Example:</p>
 * {@code
 * public void doSomething(
 * &#064;RestrictedTo({ROLE_ADMIN, ROLE_SCANNER, ROLE_USER})
 * User user
 * )
 * }
 * <p>Would require a User with both authorities to be able to access the method</p>
 * <strong>The default Authority is ROLE_ADMIN indicating that if no authority is specified then only an admin can reach the resource (fail safe).</strong>
 *
 * @since 0.0.1
 *         
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface RestrictedTo {
	Authority[] value() default Authority.ROLE_ADMIN;
}
