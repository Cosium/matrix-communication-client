package com.cosium.matrix_communication_client.test_infrastructure.synapse;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * @author Réda Housni Alaoui
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@ExtendWith(SynapseServerTestExtension.class)
public @interface EnableSynapseServer {}
