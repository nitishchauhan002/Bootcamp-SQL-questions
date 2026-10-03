WITH DailySum AS (
    SELECT 
        visited_on, 
        SUM(amount) AS total_amount
    FROM Customer
    GROUP BY visited_on
)
SELECT 
    d1.visited_on,
    SUM(d2.total_amount) AS amount,
    ROUND(AVG(d2.total_amount), 2) AS average_amount
FROM DailySum d1
JOIN DailySum d2 
  ON d2.visited_on BETWEEN DATE_SUB(d1.visited_on, INTERVAL 6 DAY) AND d1.visited_on
GROUP BY d1.visited_on
HAVING COUNT(d2.visited_on) = 7
ORDER BY d1.visited_on ASC;