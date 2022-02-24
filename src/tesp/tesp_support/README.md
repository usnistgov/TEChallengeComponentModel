# Scripts

`python3 update_glm <glm_file> <csv_file>` - update the GLM file with the values specified in the CSV (see example CSV files for reference)

`bash generate_agent_dict.sh <model_name>` - generate the double auction configuration files from a GLM file

# Required R4-12.47-1 Model Changes

Change the clock value:

```
clock {
  timezone MST7;
  starttime '2017-06-29 0:00:00';
  stoptime '2017-07-08 0:00:00';
}
```

Add a CSV reader (before the climate object):
```
object csv_reader {
  name CsvReader;
  filename "weather/tucson_minutely.csv";
};
```

Update the climate object:
```
object climate {
  reader CsvReader;
  tmyfile "weather/tucson_minutely.csv";
  //interpolate QUADRATIC;
};
```
