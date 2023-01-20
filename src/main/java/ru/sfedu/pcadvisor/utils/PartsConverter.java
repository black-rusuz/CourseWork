package ru.sfedu.pcadvisor.utils;

import com.opencsv.bean.AbstractBeanField;
import ru.sfedu.pcadvisor.model.bean.Cpu;
import ru.sfedu.pcadvisor.model.bean.Motherboard;
import ru.sfedu.pcadvisor.model.bean.Part;
import ru.sfedu.pcadvisor.model.bean.Ram;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartsConverter extends AbstractBeanField<List<Part>, String> {
    private static final String fieldsDelimiter = Constants.FIELDS_DELIMITER;
    private static final String beansDelimiter = Constants.BEANS_DELIMITER;

    private static Part stringToBean(String string) {
        String[] parsed = string.split(fieldsDelimiter);
        return switch (parsed.length) {
            case (5) -> {
                try {
                    Ram bean = new Ram();
                    bean.setId(Long.parseLong(parsed[0]));
                    bean.setName(parsed[1]);
                    bean.setPrice(Double.parseDouble(parsed[2]));
                    bean.setVolumeGb(Integer.parseInt(parsed[3]));
                    bean.setDdrVersion(Integer.parseInt(parsed[4]));
                    yield bean;
                } catch (Exception e) {
                    Motherboard bean = new Motherboard();
                    bean.setId(Long.parseLong(parsed[0]));
                    bean.setName(parsed[1]);
                    bean.setPrice(Double.parseDouble(parsed[2]));
                    bean.setSocket(parsed[3]);
                    bean.setDdrVersion(Integer.parseInt(parsed[4]));
                    yield bean;
                }
            }
            case (6) -> {
                Cpu bean = new Cpu();
                bean.setId(Long.parseLong(parsed[0]));
                bean.setName(parsed[1]);
                bean.setPrice(Double.parseDouble(parsed[2]));
                bean.setFrequency(Integer.parseInt(parsed[3]));
                bean.setCoreCount(Integer.parseInt(parsed[4]));
                bean.setSocket(parsed[5]);
                yield bean;
            }
            default -> new Part() {};
        };
    }

    public static List<Part> fromString(String string) {
        List<String> beans = List.of(string.split(beansDelimiter));
        return beans.stream().map(PartsConverter::stringToBean).toList();
    }

    private static String beanToString(Part part) {
        List<Object> params = new ArrayList<>(List.of(part.getId(), part.getName(), part.getPrice()));

        if (part instanceof Cpu cpu) {
            params.add(cpu.getFrequency());
            params.add(cpu.getCoreCount());
            params.add(cpu.getSocket());
        } else if (part instanceof Motherboard motherboard) {
            params.add(motherboard.getSocket());
            params.add(motherboard.getDdrVersion());
        } else if (part instanceof Ram ram) {
            params.add(ram.getVolumeGb());
            params.add(ram.getDdrVersion());
        }

        return params.stream().map(Object::toString).collect(Collectors.joining(fieldsDelimiter));
    }

    public static String toString(Object object) {
        List<Part> parts = (List<Part>) object;
        return parts.stream().map(PartsConverter::beanToString).collect(Collectors.joining(beansDelimiter));
    }

    @Override
    public List<Part> convert(String string) {
        return fromString(string);
    }

    @Override
    public String convertToWrite(Object object) {
        return toString(object);
    }
}
