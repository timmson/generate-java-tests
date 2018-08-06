package ${package};

import org.apache.wicket.WicketRuntimeException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ${name}Test extends AbstractTestPage {

    ${name} page;

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test(expected = WicketRuntimeException.class)
    public void testReport() {
        page = tester.startPage(${name}.class);
    }
}