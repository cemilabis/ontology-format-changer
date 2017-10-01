import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.FileOutputStream;

/**
 * Created by cemil on 01.10.2017.
 */
public class Main {

    public static void main(String[] args){

        Options options = new Options();
        options.addOption("i",true,"Input ontology");
        options.addOption("if",true,"Input ontology format");
        options.addOption("of",true,"Ontology format which input ontology will be converted to");
        options.addOption("o",true,"Output ontology");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine clArgs = parser.parse(options,args);
            String inputFile = clArgs.getOptionValue("i");
            String inputFormat = clArgs.getOptionValue("if");
            String outputFormat = clArgs.getOptionValue("of");
            String outputFile = clArgs.getOptionValue("o");

            OntModel ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
            RDFDataMgr.read(ontoModel,inputFile,getFormat(inputFormat));

            RDFDataMgr.write(new FileOutputStream(outputFile),ontoModel,getFormat(outputFormat));

        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private static Lang getFormat(String format){
        switch(format.toLowerCase()){
            case "nt":
                return Lang.NTRIPLES;
            case "nq":
                return Lang.NQUADS;
            case "xml":
                return Lang.RDFXML;
            case "json":
                return Lang.RDFJSON;
            case "ttl":
                return Lang.TURTLE;
            default:
                throw new RuntimeException(String.format("%s format is not valid",format));
        }
    }
}
