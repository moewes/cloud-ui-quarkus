package net.moewes.cloudui.quarkus.runtime;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class HtmlPageBuilder {

    @ConfigProperty(name = "quarkus.http.root-path", defaultValue = "")
    String rootPath;

    private List<String> scripts;

    public String getPage(String view) {

        String result = "<!doctype html>" +
                "<html>" +
                "<head>" +
                "<meta charset=\"utf-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">";
        String script = scripts.stream().map(item ->
                "<script src=\"" + getRootPath() + item + "\"></script>").collect(Collectors.joining());

        result = result + script + getBasicStyle() +
                "</head>" +
                "<body><cloudui-view backend=\"" + getRootPath() + "/" + view
                + "\"></cloudui-view><body>";

        return result;
    }

    private String getRootPath() {

        return "/".equals(rootPath) ? "" : rootPath;
    }

    public void setScripts(List<String> scripts) {
        this.scripts = scripts;
    }

    private String getBasicStyle() {

        return "<style>" +
                "body { margin: 0; padding: 0px } " +
                "</style>";
    }
}
