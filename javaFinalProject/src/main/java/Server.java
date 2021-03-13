package main.java;//import dao.TextDao;
//import io.javalin.Javalin;
//import io.swagger.v3.oas.models.info.Info;
//import io.javalin.plugin.openapi.OpenApiOptions;
//import io.javalin.plugin.openapi.OpenApiPlugin;
//import io.javalin.plugin.openapi.ui.ReDocOptions;
//import io.javalin.plugin.openapi.ui.SwaggerOptions;
//import org.sql2o.Sql2o;
//import service.TextService;
//
//public class Server {
//    public static void main(String[] args) throws ClassNotFoundException {
//        //TODO:connect database
//
//
//
//
//        TextDao dao;
//        TextService service = new TextService(dao);
//
//        Javalin app = Javalin.create(config -> {
//            config.registerPlugin(getConfiguredOpenApiPlugin());
//        }).start(7001);
//        app.get("/", ctx -> ctx.result("Welcome to RESTful Corpus Platform"));
//        // handle exist
//        app.get("/files/:md5/exists", service::handleExists);
//        // handle upload
//        app.post("/files/:md5", service::handleUpload);
//        // handle compare
//        app.get("/files/:md51/compare/:md52", service::handleCompare);
//        // handle download
//        app.get("/files/:md5", service::handleDownload);
//    }
//
//
//    private static OpenApiPlugin getConfiguredOpenApiPlugin() {
//        Info info = new Info().version("1.0").description("RESTful Corpus Platform API");
//        OpenApiOptions options = new OpenApiOptions(info)
//                .activateAnnotationScanningFor("cn.edu.sustech.java2.RESTfulCorpusPlatform")
//                .path("/swagger-docs") // endpoint for OpenAPI json
//                .swagger(new SwaggerOptions("/swagger-ui")); // endpoint for swagger-ui
////                .reDoc(new ReDocOptions("/redoc")); // endpoint for redoc
//        return new OpenApiPlugin(options);
//    }
//}