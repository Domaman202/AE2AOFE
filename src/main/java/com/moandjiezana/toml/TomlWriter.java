package com.moandjiezana.toml;

import static com.moandjiezana.toml.MapValueWriter.MAP_VALUE_WRITER;
import static com.moandjiezana.toml.ObjectValueWriter.OBJECT_VALUE_WRITER;
import static com.moandjiezana.toml.ValueWriters.WRITERS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * <p>Converts Objects to TOML</p>
 *
 * <p>An input Object can comprise arbitrarily nested combinations of Java primitive types,
 * other {@link Object}s, {@link Map}s, {@link List}s, and Arrays. {@link Object}s and {@link Map}s
 * are output to TOML tables, and {@link List}s and Array to TOML arrays.</p>
 *
 * <p>Example usage:</p>
 * <pre><code>
 * class AClass {
 *   int anInt = 1;
 *   int[] anArray = { 2, 3 };
 * }
 *
 * String tomlString = new TomlWriter().write(new AClass());
 * </code></pre>
 */
public class TomlWriter {
  private final IndentationPolicy indentationPolicy;
  private final DatePolicy datePolicy;

  private TomlWriter(int keyIndentation, int tableIndentation, int arrayDelimiterPadding, TimeZone timeZone, boolean showFractionalSeconds) {
    this.indentationPolicy = new IndentationPolicy(keyIndentation, tableIndentation, arrayDelimiterPadding);
    this.datePolicy = new DatePolicy(timeZone, showFractionalSeconds);
  }

  /**
   * Write an Object into TOML String.
   *
   * @param from the object to be written
   * @return a string containing the TOML representation of the given Object
   */
  public String write(Object from) {
    try {
      StringWriter output = new StringWriter();
      write(from, output);
      
      return output.toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Write an Object in TOML to a {@link File}. Output is encoded as UTF-8.
   *
   * @param from the object to be written
   * @param target the File to which the TOML will be written
   * @throws IOException if any file operations fail
   */
  public void write(Object from, File target) throws IOException {
    try (OutputStream outputStream = new FileOutputStream(target)) {
      write(from, outputStream);
    }
  }

  /**
   * Write an Object in TOML to a {@link OutputStream}. Output is encoded as UTF-8.
   *
   * @param from the object to be written
   * @param target the OutputStream to which the TOML will be written. The stream is NOT closed after being written to.
   * @throws IOException if target.write() fails
   */
  public void write(Object from, OutputStream target) throws IOException {
    OutputStreamWriter writer = new OutputStreamWriter(target, StandardCharsets.UTF_8);
    write(from, writer);
    writer.flush();
  }

  /**
   * Write an Object in TOML to a {@link Writer}. You MUST ensure that the {@link Writer}s's encoding is set to UTF-8 for the TOML to be valid.
   *
   * @param from the object to be written. Can be a Map or a custom type. Must not be null.
   * @param target the Writer to which TOML will be written. The Writer is not closed.
   * @throws IOException if target.write() fails
   * @throws IllegalArgumentException if from is of an invalid type
   */
  public void write(Object from, Writer target) throws IOException {
    ValueWriter valueWriter = WRITERS.findWriterFor(from);
    if (valueWriter == MAP_VALUE_WRITER || valueWriter == OBJECT_VALUE_WRITER) {
      WriterContext context = new WriterContext(indentationPolicy, datePolicy, target);
      valueWriter.write(from, context);
    } else {
      throw new IllegalArgumentException("An object of class " + from.getClass().getSimpleName() + " cannot produce valid TOML. Please pass in a Map or a custom type.");
    }
  }
}
