package br.com.meta.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

/**
 * @author samuel-cruz
 *
 */
@Data
@MappedSuperclass
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "dataCriacao", nullable = false, updatable = false)
	@CreatedDate
	@Setter(AccessLevel.PROTECTED)
	private Timestamp dataCriacao;

	@Column(name = "dataAlteracao", nullable = false)
	@LastModifiedDate
	@Setter(AccessLevel.PROTECTED)
	private Timestamp dataAlteracao;

}
