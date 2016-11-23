package ru.mitrakov.self.cdm.client.json;

import ru.mitrakov.self.cdm.client.json.commands.*;
import ru.mitrakov.self.cdm.client.json.commands.cmd.*;
import java.util.*;
import java.io.IOException;
import org.codehaus.jackson.node.*;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;



/**
 * Json object parser
 * @author Tommy
 */
public class Parser {
    public static Cmd parseString(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(json);
            JsonNode cmdNode = rootNode.get("cmd");
            JsonNode argsNode = rootNode.get("args");
            JsonNode sidNode = rootNode.get("id");
            switch(cmdNode.asText()) {
                case "log":
                    return new Login(argsNode.get("name").asText(), argsNode.get("pwd").asText());
                case "sup":
                    return new SignUp(argsNode.get("name").asText(), argsNode.get("pwd").asText(), argsNode.get("mail").asText());
                case "inv":
                    return new Invite(sidNode.asInt(), rootNode.get("tok").asText(), argsNode.get("uid").asInt());
                case "acc":
                    return new Accept(sidNode.asInt(), rootNode.get("tok").asText(), argsNode.get("uid").asInt());
                case "rej":
                    return new Reject(sidNode.asInt(), rootNode.get("tok").asText(), argsNode.get("uid").asInt());
                case "gup":
                    return new GiveUp(sidNode.asInt(), rootNode.get("tok").asText());
                case "mov":
                    return new Move(sidNode.asInt(), rootNode.get("tok").asText(), argsNode.get("unit").asInt(), argsNode.get("x").asInt(), argsNode.get("y").asInt());
                case "str":
                    return new Strike(sidNode.asInt(), rootNode.get("tok").asText(), argsNode.get("unit").asInt(), argsNode.get("wpn").asInt(), argsNode.get("dir").asInt(), argsNode.get("ang").asInt(), argsNode.get("str").asInt(), argsNode.get("spn").asInt());
                case "uid":
                    return new GetUserId(sidNode.asInt(), rootNode.get("tok").asText(), argsNode.get("name").asText());
                case "rid":
                    return new GetRandUserId(sidNode.asInt(), rootNode.get("tok").asText());
                case "guu":
                    return new GetUnits(sidNode.asInt(), rootNode.get("tok").asText());
                case "ruu":
                    return new RenameUnit(sidNode.asInt(), rootNode.get("tok").asText(), argsNode.get("unit").asInt(), argsNode.get("name").asText());
                case "_ok":
                    return new ResponseOk(sidNode.asInt());
                case "_err":
                    return new ResponseError(sidNode.asInt(), argsNode.get("code").asInt(), argsNode.get("err").asText());
                case "_tok":
                    return new ResponseToken(sidNode.asInt(), argsNode.get("tok").asText());
                case "_uid":
                    return new ResponseUserId(sidNode.asInt(), argsNode.get("name").asText(), argsNode.get("uid").asInt());
                case "_end":
                    return new ResponseFinished(sidNode.asInt(), argsNode.get("winner").asText());
                case "_st":
                    List<String> lst = new LinkedList<>();
                    for (JsonNode node : argsNode.get("state"))
                        lst.add(node.asText());
                    return new ResponseState(sidNode.asInt(), lst);
                case "_uu":
                    List<String> names = new LinkedList<>();
                    for (JsonNode node : argsNode.get("names"))
                        names.add(node.asText());
                    return new ResponseUnits(sidNode.asInt(), names, argsNode.get("cap").asInt());
                case "_ac":
                    List<Integer> lst1 = new LinkedList<>();
                    List<String> lst2 = new LinkedList<>();
                    for (JsonNode node : argsNode.get("path"))
                        lst1.add(node.asInt());
                    for (JsonNode node : argsNode.get("state"))
                        lst2.add(node.asText());
                    return new ResponseAction(sidNode.asInt(), lst1, lst2);
                default: break;
            }
            
            return new ResponseError(sidNode.asInt(), 225, String.format("parse error. %s", json));
        } catch (Exception e) {
            return new ResponseError(0, 226, String.format("parse error: %s", json));
        }
    }
    
    public static String convertToJson(Cmd cmd) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        // preparing objects/arrays
        ObjectNode rootNode = mapper.createObjectNode();
        ObjectNode argsNode = mapper.createObjectNode();
        ArrayNode stateNode = mapper.createArrayNode();
        ArrayNode namesNode = mapper.createArrayNode();
        ArrayNode pathNode  = mapper.createArrayNode();
        
        // complete necessary fields ("id" and "cmd")
        rootNode.put("id", cmd instanceof CmdSid ? ((CmdSid)cmd).sid : 0);
        rootNode.put("cmd", cmd.cmd);
        
        // complete "token" (only for Requests)
        if (cmd instanceof CmdRequest)
            rootNode.put("tok", ((CmdRequest)cmd).token);
        
        // complete fields depending on the Command
        if (cmd instanceof Login) {
            rootNode.put("args", argsNode);
            argsNode.put("name", ((Login)cmd).name);
            argsNode.put("pwd",  ((Login)cmd).password);
        } else if (cmd instanceof SignUp) {
            rootNode.put("args", argsNode);
            argsNode.put("name", ((SignUp)cmd).name);
            argsNode.put("pwd",  ((SignUp)cmd).password);
            argsNode.put("mail", ((SignUp)cmd).email);
        } else if (cmd instanceof Invite) {
            rootNode.put("args", argsNode);
            argsNode.put("uid", ((Invite)cmd).userId);
        } else if (cmd instanceof Accept) {
            rootNode.put("args", argsNode);
            argsNode.put("uid", ((Accept)cmd).enemySid);
        } else if (cmd instanceof Reject) {
            rootNode.put("args", argsNode);
            argsNode.put("uid", ((Reject)cmd).enemySid);
        } else if (cmd instanceof GiveUp) {
            // OK
        } else if (cmd instanceof Move) {
            rootNode.put("args", argsNode);
            argsNode.put("unit", ((Move)cmd).unitIdx);
            argsNode.put("x", ((Move)cmd).x);
            argsNode.put("y", ((Move)cmd).y);
        } else if (cmd instanceof Strike) {
            rootNode.put("args", argsNode);
            argsNode.put("unit", ((Strike)cmd).unit);
            argsNode.put("wpn", ((Strike)cmd).weapon);
            argsNode.put("dir", ((Strike)cmd).direction);
            argsNode.put("ang", ((Strike)cmd).angle);
            argsNode.put("str", ((Strike)cmd).strength);
            argsNode.put("spn", ((Strike)cmd).spin);
        } else if (cmd instanceof GetUserId) {
            rootNode.put("args", argsNode);
            argsNode.put("name", ((GetUserId)cmd).name);
        } else if (cmd instanceof GetRandUserId) {
            // OK
        } else if (cmd instanceof GetUnits) {
            // OK
        } else if (cmd instanceof RenameUnit) {
            rootNode.put("args", argsNode);
            argsNode.put("unit", ((RenameUnit)cmd).unitIdx);
            argsNode.put("name", ((RenameUnit)cmd).name);
        } else if (cmd instanceof ResponseOk) {
            // OK
        } else if (cmd instanceof ResponseError) {
            rootNode.put("args", argsNode);
            argsNode.put("code", ((ResponseError)cmd).code);
            argsNode.put("err",  ((ResponseError)cmd).error);
        } else if (cmd instanceof ResponseToken) {
            rootNode.put("args", argsNode);
            argsNode.put("tok", ((ResponseToken)cmd).token);
        } else if (cmd instanceof ResponseUserId) {
            rootNode.put("args", argsNode);
            argsNode.put("name", ((ResponseUserId)cmd).name);
            argsNode.put("uid",  ((ResponseUserId)cmd).userId);
        } else if (cmd instanceof ResponseFinished) {
            rootNode.put("args", argsNode);
            argsNode.put("winner", ((ResponseFinished)cmd).winnerName);
        } else if (cmd instanceof ResponseState) {
            rootNode.put("args", argsNode);
            for (String s : ((ResponseState)cmd).state)
                stateNode.add(s);
            argsNode.put("state", stateNode);
        } else if (cmd instanceof ResponseUnits) {
            rootNode.put("args", argsNode);
            for (String name : ((ResponseUnits)cmd).names)
                namesNode.add(name);
            argsNode.put("names", namesNode);
            argsNode.put("cap", ((ResponseUnits)cmd).captainId);
        } else if (cmd instanceof ResponseAction) {
            rootNode.put("args", argsNode);
            for (int i : ((ResponseAction)cmd).path)
                pathNode.add(i);
            for (String s : ((ResponseAction)cmd).state)
                pathNode.add(s);
            argsNode.put("path", pathNode);
            argsNode.put("state", stateNode);
        } else {
            // error occured
            rootNode.put("id", 0);
            rootNode.put("cmd", "err");
            rootNode.put("args", argsNode);
            argsNode.put("err", String.format("unknown command %s", cmd.toString()));
        }
            
        return mapper.writeValueAsString(rootNode);
    }
}
