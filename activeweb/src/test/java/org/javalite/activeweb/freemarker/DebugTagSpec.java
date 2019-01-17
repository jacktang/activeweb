
package org.javalite.activeweb.freemarker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import java.io.IOException;
import java.io.StringWriter;

import static org.javalite.common.Collections.map;
import static org.javalite.test.jspec.JSpec.a;

/**
 * @author Igor Polevoy
 */
public class DebugTagSpec  {

    FreeMarkerTemplateManager manager = new FreeMarkerTemplateManager();
    StringWriter sw = new StringWriter();

    @BeforeEach
    public void before() throws IOException, ServletException, IllegalAccessException, InstantiationException {
        manager.setTemplateLocation("src/test/views");
    }

    @Test
    public void shouldPrintDebugInformationForMap() {
        manager.merge(map("context_path", "/bookstore", "activeweb", map("controller", "simple")),
                "/debug/debug", sw);
        a(sw.toString()).shouldContain("{controller=simple}");
    }

    @Test
    public void shouldPrintSimpleValue() {
        manager.merge(map("context_path", "/bookstore", "activeweb", map("controller", "simple"), "message", "hello"),
                "/debug/message", sw);
        a(sw.toString()).shouldBeEqual("hello");
    }


    @Test
    public void shouldPrintNull() {
        manager.merge(map("context_path", "/bookstore", "activeweb", map("controller", "simple"), "greeting", null),
                "/debug/greeting", sw);
        a(sw.toString()).shouldBeEqual("DebugTag: value 'print' is null!");
    }
}
