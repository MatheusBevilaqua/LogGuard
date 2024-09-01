import psutil  as p

print("\n", p.cpu_times(percpu=True))
print("\n", p.cpu_times())
print("\n", p.cpu_percent(interval=None, percpu=False))

print("\n", p.cpu_times())