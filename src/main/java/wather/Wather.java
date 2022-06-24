package wather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wather{
	private int dt;
	private Coord coord;
	private int visibility;
	private List<WeatherItem> weather;
	private String name;
	private int cod;
	private Main main;
	private Clouds clouds;
	private int id;
	private Sys sys;
	private String base;
	private Wind wind;
}