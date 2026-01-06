package com.example.authentication.shared.infrastructure.persistence.jpa.configuration.strategy;

import static io.github.encryptorcode.pluralize.Pluralize.pluralize;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Custom Hibernate PhysicalNamingStrategy that converts all entity names and columns to snake_case
 * and pluralizes table names.
 *
 * <p>This strategy ensures that database tables follow the snake_case naming convention and
 * table names are pluralized (e.g., User -> users).</p>
 */
public class SnakeCaseWithPluralizedTablePhysicalNamingStrategy implements PhysicalNamingStrategy {

  @Override
  public Identifier toPhysicalCatalogName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
    return this.toSnakeCase(identifier);
  }

  @Override
  public Identifier toPhysicalSchemaName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
    return this.toSnakeCase(identifier);
  }

  @Override
  public Identifier toPhysicalTableName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
    return this.toSnakeCase(this.toPlural(identifier));
  }

  @Override
  public Identifier toPhysicalSequenceName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
    return this.toSnakeCase(identifier);
  }

  @Override
  public Identifier toPhysicalColumnName(Identifier identifier, JdbcEnvironment jdbcEnvironment) {
    return this.toSnakeCase(identifier);
  }

  private Identifier toSnakeCase(final Identifier identifier) {
    if (identifier == null) {
      return null;
    }
    final String regex = "([a-z])([A-Z])";
    final String replacement = "$1_$2";
    final String newName = identifier.getText()
        .replaceAll(regex, replacement)
        .toLowerCase();
    return Identifier.toIdentifier(newName);
  }

  private Identifier toPlural(final Identifier identifier) {
    final String newName = pluralize(identifier.getText());
    return Identifier.toIdentifier(newName);
  }
}
